package game;

import gameio.GameSerialization;
import maps.GameMap;
import mvc.CommandController;
import mvc.MainController;
import mvc.View;
import playercharacter.*;
import quests.Quest;

import java.io.IOException;
import java.util.List;

public class Game {

    private static final View view = new View();
    private static final MainController controller = new MainController();

    public void gameLoop(PlayerCharacter character, GameMap map, List<Quest> questList,
                                MutableBoolean won, MutableBoolean dead, MutableBoolean quit,
                                long startTime, String saveName, CommandController commandController) {

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
                    view.outputln("Error: " + e.getMessage());
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            controller.handleCommandInput(view, commandController, lock, character, map,
                            questList, startTime, saveName, quit);

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
            view.outputln("Quitting game...");
            view.outputln("\nCharacter stats:");
            commandController.stats(character);
            view.outputln();
            commandController.save(character, map, startTime, saveName);
        } else if (won.getValue() == true) {
            view.outputln("You have won the game!");
            view.outputln("\nCharacter stats:");
            commandController.stats(character);
            view.outputln();
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                view.outputln("Couldn't delete save: ");
            }
        } else if (dead.getValue() == true) {
            view.outputln("You have died!");
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                view.outputln("Couldn't delete save: ");
            }
        }
    }

}
