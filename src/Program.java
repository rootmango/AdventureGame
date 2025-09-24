import game.GameLoopCoreParams;
import game.GameLoopMutableBooleans;
import mvc.controllers.CommandController;
import mvc.controllers.MainController;
import mvc.controllers.PromptController;
import gameexceptions.CharacterNotFoundException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import playercharacter.*;
import quests.*;
import game.Game;
import game.MutableBoolean;

import java.io.IOException;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        final MainView mainView = new MainView();
        final QuestView questView = new QuestView();
        final CharacterView characterView = new CharacterView();
        final GameMapView mapView = new GameMapView();

        final CommandController commandController = new CommandController(mainView, questView, characterView);
        final MainController mainController = new MainController();
        final Game game = new Game(mainView, mainController, characterView);
        final GameSaveView gameSaveView = new GameSaveView();
        final PromptYesNoView promptYesNoView = new PromptYesNoView();
        final PromptController promptController = new PromptController(mainView, gameSaveView,
                                                                        characterView, promptYesNoView);

        try {

            PlayerCharacter character;
            GameMap map;
            String saveName = "";
            long startTime;

            boolean startNewGame = promptController.promptNewGame();

            if (startNewGame) {
                saveName = promptController.promptNewSaveName();
                map = new GameMap(mainView);
                String characterType = promptController.promptCharacterType();
                character = new PlayerCharacter(characterType, mainView, mapView);
                character.findCharacterAndSetCoordinates(map);
                startTime = System.currentTimeMillis();
                GameSerialization.createOrOverwriteSave(character, map, startTime, saveName);
                mainView.outputln("New save \"" + saveName + "\" created successfully!");

                commandController.printHelpCommands();
            } else {
                saveName = promptController.promptLoadGame();
                map = GameSerialization.readGameMapFromSave(saveName);
                character = GameSerialization.readPlayerCharacterFromSave(saveName);
                long elapsedTime = GameSerialization.readElapsedTimeFromSave(saveName);
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
            commandController.quests(questList);
            mainView.outputln();
            commandController.map(character, map);
            mainView.outputln(
                    "Try moving to a desired direction (for example, \"move north\") or looking around " +
                            "(\"look\")");

            var gameLoopCoreParams = new GameLoopCoreParams(character, map, questList, saveName);
            var gameLoopMutableBooleans = new GameLoopMutableBooleans(won, dead, quit);
            game.gameLoop(gameLoopCoreParams, gameLoopMutableBooleans, startTime, commandController);
        } catch (IOException e) {
            mainView.outputln("Error: " + e.getMessage());
        } catch (CharacterNotFoundException e) {
            mainView.outputln("Error in map file: " + e.getMessage());
        }
    }
}
