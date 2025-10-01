package game;

import commands.SaveCommand;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.controllers.MainController;
import mvc.observers.CommandObserver;
import mvc.observers.GameObserver;
import mvc.views.CharacterView;
import mvc.views.MainView;
import mvc.views.PromptView;
import mvc.views.QuestView;
import playercharacter.*;
import quests.Quest;

import java.io.IOException;
import java.util.List;

public class Game {

    protected final MainView mainView;
    protected final MainController mainController;
    protected final CharacterView characterView;
    protected final GameObserver gameObserver;
    protected final PromptView promptView;
    protected final QuestView questView;
    protected final CommandObserver commandObserver;
    protected final GameSerialization gameSerialization;
    protected final GameTime gameTime;

    public Game(MainView mainView, MainController mainController, CharacterView characterView,
                GameObserver gameObserver, PromptView promptView, QuestView questView,
                CommandObserver commandObserver, GameSerialization gameSerialization, GameTime gameTime) {
        this.mainView = mainView;
        this.mainController = mainController;
        this.characterView = characterView;
        this.gameObserver = gameObserver;
        this.promptView = promptView;
        this.questView = questView;
        this.commandObserver = commandObserver;
        this.gameSerialization = gameSerialization;
        this.gameTime = gameTime;
    }

    public void gameLoop(GameLoopCoreParams gameLoopCoreParams,
                         GameLoopMutableBooleans gameLoopMutableBooleans,
                         long startTime) {

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
                        gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTime);
                    }
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    run = false;
                } catch (IOException e) {
                    gameObserver.generalIOError(e);
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            mainController.handleCommandInput(mainView, lock, gameLoopCoreParams,
                                                startTime, quit, promptView, questView,
                                                characterView, commandObserver, gameSerialization,
                                                gameTime);
            synchronized (lock) {
                questList.forEach(Quest::setOrUpdateCompleted);
                if (character.isDead()) {
                    autosaveThread.interrupt();
                    dead.setValue(true);
                }
            }
        }

        autosaveThread.interrupt();
        onExit(character, map, won, dead, quit, startTime, saveName, characterView, gameSerialization);

    }

    private void onExit(PlayerCharacter character, GameMap map, MutableBoolean won,
                               MutableBoolean dead, MutableBoolean quit, long startTime, String saveName,
                               CharacterView characterView, GameSerialization gameSerialization) {
        if (quit.getValue() == true) {
            gameObserver.quitGame(character, characterView);
            new SaveCommand(character, map, startTime, saveName, gameSerialization, gameTime).execute();
        } else if (won.getValue() == true) {
            gameObserver.won(character, characterView, startTime);
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                gameObserver.errorDeletingSave(e);
            }
        } else if (dead.getValue() == true) {
            gameObserver.died(startTime);
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                gameObserver.errorDeletingSave(e);
            }
        }
    }

}
