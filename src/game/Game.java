package game;

import commands.SaveCommand;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.controllers.CommandController;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandViewInterface;
import mvc.views.gameviews.GameViewInterface;
import mvc.views.promptviews.PromptView;
import playercharacter.*;
import quests.Quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    protected final MainView mainView;
    protected final CommandController commandController;
    protected final CharacterView characterView;
    protected final PromptView promptView;
    protected final QuestView questView;
    protected final List<CommandViewInterface> commandViews;
    protected final GameSerialization gameSerialization;
    protected final GameTime gameTime;
    protected final List<GameViewInterface> observers = new ArrayList<>();

    public void addObservers(List<GameViewInterface> observers) {
        this.observers.addAll(observers);
    }

    public Game(MainView mainView, CharacterView characterView,
                List<CommandViewInterface> commandViews,
                GameSerialization gameSerialization, GameTime gameTime) {
        this.mainView = mainView;
        commandController = new CommandController();
        this.characterView = characterView;
        promptView = new PromptView();
        questView = new QuestView();
        this.commandViews = commandViews;
        this.gameSerialization = gameSerialization;
        this.gameTime = gameTime;
    }

    public Game(MainView mainView, CharacterView characterView,
                List<CommandViewInterface> commandViews,
                GameSerialization gameSerialization, GameTime gameTime,
                List<GameViewInterface> observers) {
        this(mainView, characterView, commandViews, gameSerialization, gameTime);
        this.observers.addAll(observers);
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
                    observers.forEach(observer -> observer.onIOError(e));
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            commandController.handleCommandInput(mainView, lock, gameLoopCoreParams,
                                                startTime, quit, promptView, questView,
                                                characterView, gameSerialization,
                                                commandViews, gameTime);
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
            observers.forEach(observer -> observer.onQuit(character, characterView));
            new SaveCommand(character, map, startTime, saveName, gameSerialization, gameTime, commandViews)
                    .execute();
        } else if (won.getValue() == true) {
            observers.forEach(observer -> observer.onWon(character, characterView, startTime));
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                observers.forEach(observer -> observer.onErrorDeletingSave(e));
            }
        } else if (dead.getValue() == true) {
            observers.forEach(observer -> observer.onDied(startTime));
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                observers.forEach(observer -> observer.onErrorDeletingSave(e));
            }
        }
    }

}
