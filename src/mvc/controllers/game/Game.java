package mvc.controllers.game;

import commands.MapCommand;
import commands.QuestsCommand;
import commands.SaveCommand;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.controllers.*;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandEventListener;
import mvc.views.commandviews.CommandView;
import mvc.views.gameviews.GameView;
import mvc.views.gameviews.GameCLIViews;
import mvc.views.promptviews.PromptView;
import playercharacter.*;
import quests.CollectXPForFortress;
import quests.DefeatTheGoblinKing;
import quests.Quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    protected GameView gameView;
    protected CommandController commandController;
    protected CharacterView characterView;
    protected PromptView promptView;
    protected QuestView questView;
    protected CommandEventListener commandEventListener;
    protected GameSerialization gameSerialization;
    protected GameTimeUtils gameTimeUtils;
    protected GameSaveView gameSaveView;
    protected CommandView commandView;

    protected PlayerCharacter character;
    protected GameMap map;
    protected String saveName = "";
    protected long startTime;
    protected MutableBoolean won;
    protected MutableBoolean dead;
    protected MutableBoolean quit;
    protected List<Quest> questList;

    public Game(GameCLIViews gameCLIViews, CoreGameStateBundle coreGameStateBundle,
                CommandController commandController, GameSerialization gameSerialization)
            throws IOException {
        this.commandController = commandController;
        this.gameTimeUtils = new GameTimeUtils();
        this.gameSerialization = gameSerialization;
        initializeGame(gameCLIViews, coreGameStateBundle);
    }

    protected void initializeGame(GameCLIViews gameCLIViews, CoreGameStateBundle coreGameStateBundle)
            throws IOException {
        character = coreGameStateBundle.character();
        map = coreGameStateBundle.map();
        saveName = coreGameStateBundle.saveName();
        startTime = coreGameStateBundle.startTime();

        gameView = gameCLIViews.gameView();
        characterView = gameCLIViews.characterView();
        gameSaveView = gameCLIViews.gameSaveView();
        commandView = gameCLIViews.commandView();
        promptView = gameCLIViews.promptView();
        commandEventListener = gameCLIViews.commandView();

        won = new MutableBoolean(false);
        dead = new MutableBoolean(false);
        quit = new MutableBoolean(false);
        questList = new ArrayList<>(List.of(
                new CollectXPForFortress(character, map.getFortress()),
                new DefeatTheGoblinKing(won, map.getFortress())
        ));

        questList.forEach(Quest::setOrUpdateCompleted);
        // necessary if we are loading a game and the conditions are already met

        showIntroMessage();
    }

    protected void showIntroMessage() {
        gameView.outputln("Quests:");
        new QuestsCommand(questList, commandView).execute();
        gameView.outputln();
        new MapCommand(character, map, commandView).execute();
        gameView.outputln(
                "Try moving to a desired direction (for example, \"move north\") or looking around " +
                        "(\"look\")");
    }

    public void gameLoop() {

        final Object lock = new Object();

        Thread autosaveThread = new Thread(() -> {
            boolean run = true;
            while (run) {
                try {
                    synchronized (lock) {
                        gameSerialization.createOrOverwriteSave(
                                character, map, startTime, saveName, gameTimeUtils);
                    }
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    run = false;
                } catch (IOException e) {
                    gameView.onIOError(e);
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            var commandGameStateBundle = new CommandGameStateBundle(character, map, questList, saveName, quit);
            var commandViewsBundle = new CommandViewsBundle(commandView, promptView, questView, characterView);
            var commandTimeBundle = new CommandTimeBundle(startTime, gameTimeUtils);

            commandController.handleCommandInput(
                    lock, commandGameStateBundle, commandViewsBundle, commandTimeBundle, gameSerialization);

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

    protected void onExit(PlayerCharacter character, GameMap map, MutableBoolean won,
                               MutableBoolean dead, MutableBoolean quit, long startTime, String saveName,
                               CharacterView characterView, GameSerialization gameSerialization) {
        if (quit.getValue() == true) {
            gameView.onQuit(character, characterView);
            new SaveCommand(character, map, startTime, saveName, gameSerialization,
                    gameTimeUtils, commandEventListener)
                    .execute();
        } else if (won.getValue() == true) {
            gameView.onWon(character, characterView, startTime);
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                gameView.onErrorDeletingSave(e);
            }
        } else if (dead.getValue() == true) {
            gameView.onDied(startTime);
            try {
                gameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                gameView.onErrorDeletingSave(e);
            }
        }
    }

}
