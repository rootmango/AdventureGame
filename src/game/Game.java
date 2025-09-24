package game;

import gameio.GameSerialization;
import maps.GameMap;
import mvc.controllers.CommandController;
import mvc.controllers.MainController;
import mvc.views.CharacterView;
import mvc.views.MainView;
import playercharacter.*;
import quests.Quest;

import java.io.IOException;
import java.util.List;

public class Game {

    protected final MainView mainView;
    protected final MainController mainController;
    protected final CharacterView characterView;

    public Game(MainView mainView, MainController mainController, CharacterView characterView) {
        this.mainView = mainView;
        this.mainController = mainController;
        this.characterView = characterView;
    }

    public void gameLoop(GameLoopCoreParams gameLoopCoreParams,
                         GameLoopMutableBooleans gameLoopMutableBooleans,
                         long startTime, CommandController commandController) {

        PlayerCharacter character = gameLoopCoreParams.character();
        GameMap map = gameLoopCoreParams.map();
        List<Quest> questList = gameLoopCoreParams.questList();
        String saveName = gameLoopCoreParams.saveName();
        MutableBoolean won = gameLoopMutableBooleans.won();
        MutableBoolean dead = gameLoopMutableBooleans.dead();
        MutableBoolean quit = gameLoopMutableBooleans.quit();

        final Object lock = new Object();

        Thread autosaveThread = new Thread(() -> {
            boolean run = true;
            while (run) {
                try {
                    synchronized (lock) {
                        GameSerialization.createOrOverwriteSave(character, map, startTime, saveName);
                    }
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    run = false;
                } catch (IOException e) {
                    mainView.outputln("Error: " + e.getMessage());
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            mainController.handleCommandInput(mainView, commandController, lock,
                                            gameLoopCoreParams, startTime, quit, characterView);

            synchronized (lock) {
                questList.forEach(Quest::setOrUpdateCompleted);
                if (character.isDead()) {
                    autosaveThread.interrupt();
                    dead.setValue(true);
                }
            }

        }

        autosaveThread.interrupt();
        onExit(character, map, won, dead, quit, startTime, saveName, commandController);

    }

    private void onExit(PlayerCharacter character, GameMap map, MutableBoolean won,
                               MutableBoolean dead, MutableBoolean quit, long startTime, String saveName,
                               CommandController commandController) {
        if (quit.getValue() == true) {
            mainView.outputln("Quitting game...");
            mainView.outputln("\nCharacter stats:");
            commandController.stats(character);
            mainView.outputln();
            commandController.save(character, map, startTime, saveName);
        } else if (won.getValue() == true) {
            mainView.outputln("You have won the game!");
            mainView.outputln("\nCharacter stats:");
            commandController.stats(character);
            mainView.outputln();
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                mainView.outputln("Couldn't delete save: ");
            }
        } else if (dead.getValue() == true) {
            mainView.outputln("You have died!");
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                mainView.outputln("Couldn't delete save: ");
            }
        }
    }

}
