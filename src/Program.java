import commands.MapCommand;
import commands.QuestsCommand;
import game.*;
import gameio.DeserializedBundle;
import gamerandom.GameRandom;
import mvc.controllers.MainController;
import mvc.controllers.PromptController;
import gameexceptions.CharacterNotFoundException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.observers.*;
import mvc.views.*;
import playercharacter.*;
import quests.*;

import java.io.IOException;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        final GameSerialization gameSerialization = new GameSerialization();

        final MainView mainView = new MainView();
        final QuestView questView = new QuestView();
        final GameMapView mapView = new GameMapView();
        final CharacterView characterView = new CharacterView(mapView);
        final PlaceView placeView = new PlaceView();
        final EnemyView enemyView = new EnemyView();
        final ItemView itemView = new ItemView();
        final GameView gameView = new GameView();
        final GameSaveView gameSaveView = new GameSaveView(gameSerialization);
        final PromptYesNoValidation promptYesNoValidation = new PromptYesNoValidation();
        final PromptView promptView = new PromptView();
        final CommandView commandView = new CommandView();

        final CharacterObserver characterObserver = new CharacterObserver(characterView);
        final PlaceObserver placeObserver = new PlaceObserver(placeView);
        final EnemyObserver enemyObserver = new EnemyObserver(enemyView);
        final ItemObserver itemObserver = new ItemObserver(itemView);
        final GameObserver gameObserver = new GameObserver(gameView);
        final CommandObserver commandObserver = new CommandObserver(commandView);
        final PromptObserver promptObserver = new PromptObserver(promptView);

        final MainController mainController = new MainController();
        final PromptController promptController = new PromptController(mainView, gameSaveView,
                characterView, promptYesNoValidation, promptObserver);

        final GameTime gameTime = new GameTime();
        final Game game = new Game(mainView, mainController, characterView, gameObserver,
                                    promptView, questView, commandObserver, gameSerialization, gameTime);
        final GameRandom gameRandom = new GameRandom();

        try {

            PlayerCharacter character;
            GameMap map;
            String saveName = "";
            long startTime;

            boolean startNewGame = promptController.promptNewGame();

            if (startNewGame) {
                saveName = promptController.promptNewSaveName();
                map = new GameMap(enemyObserver, itemObserver, gameRandom);
                String characterType = promptController.promptCharacterType();
                character = new PlayerCharacter(characterType, mainView, mapView, characterObserver,
                                                placeObserver);
                character.findCharacterAndSetCoordinates(map);
                startTime = System.currentTimeMillis();
                gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTime);
                mainView.outputln("New save \"" + saveName + "\" created successfully!");

                commandView.showHelpCommands();
            } else {
                saveName = promptController.promptLoadGame();
                DeserializedBundle deserializedBundle = gameSerialization.readFromSave(saveName);
                map = deserializedBundle.map();
                character = deserializedBundle.character();
                long elapsedTime = deserializedBundle.elapsedTime();
                startTime = System.currentTimeMillis() - elapsedTime;
                // "adds" the elapsed time from the previous session to the current session.
                // By subtracting the number of elapsed milliseconds from the current time,
                // we're making it as if the current session started X amount of milliseconds
                // earlier.
                mainView.outputln("Loaded save \"" + saveName + "\"");
            }

            MutableBoolean won = new MutableBoolean(false);
            MutableBoolean dead = new MutableBoolean(false);
            MutableBoolean quit = new MutableBoolean(false);
            List<Quest> questList = new ArrayList<>(List.of(
                    new CollectXPForFortress(character, map.getFortress(map)),
                    new DefeatTheGoblinKing(won, map.getFortress(map))
            ));

            questList.forEach(Quest::setOrUpdateCompleted);
            // necessary if we are loading a game and the conditions are already met
            mainView.outputln("Quests:");
            new QuestsCommand(questList).execute();
            mainView.outputln();
            new MapCommand(character, map).execute();
            mainView.outputln(
                    "Try moving to a desired direction (for example, \"move north\") or looking around " +
                            "(\"look\")");

            var gameLoopCoreParams = new GameLoopCoreParams(character, map, questList, saveName);
            var gameLoopMutableBooleans = new GameLoopMutableBooleans(won, dead, quit);
            game.gameLoop(gameLoopCoreParams, gameLoopMutableBooleans, startTime);
        } catch (IOException e) {
            mainView.outputln("Error: " + e.getMessage());
        } catch (CharacterNotFoundException e) {
            mainView.outputln("Error in map file: " + e.getMessage());
        }
    }
}
