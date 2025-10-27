import commands.MapCommand;
import commands.QuestsCommand;
import game.*;
import gameio.DeserializedBundle;
import gamerandom.RandomCommonEnemyGenerator;
import gamerandom.RandomItemContainerGenerator;
import mvc.controllers.PromptController;
import gameexceptions.CharacterNotFoundException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.characterviews.CharacterObserver;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandEventListener;
import mvc.views.enemyviews.EnemyView;
import mvc.views.enemyviews.EnemyObserver;
import mvc.views.gameviews.GameView;
import mvc.views.gameviews.GameObserver;
import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemObserver;
import mvc.views.placeviews.PlaceView;
import mvc.views.placeviews.PlaceObserver;
import mvc.views.promptviews.PromptView;
import mvc.views.promptviews.PromptEventListener;
import playercharacter.*;
import quests.*;

import java.io.IOException;
import java.util.*;

public class Program {

    public static void main(String[] args) {
        final GameSerialization gameSerialization = new GameSerialization();

        final MainView mainView = new MainView();
        final GameMapView mapView = new GameMapView();
        final CharacterView characterView = new CharacterView(mapView);
        final GameSaveView gameSaveView = new GameSaveView(gameSerialization);
        final PromptYesNoValidation promptYesNoValidation = new PromptYesNoValidation();

        final List<CharacterObserver> characterObservers
                = new ArrayList<>(List.of(new CharacterView(mapView)));
        final List<PlaceObserver> placeObservers = new ArrayList<>(List.of(new PlaceView()));
        final List<EnemyObserver> enemyObservers = new ArrayList<>(List.of(new EnemyView()));
        final List<ItemObserver> itemObservers = new ArrayList<>(List.of(new ItemView()));
        final List<GameObserver> gameObservers = new ArrayList<>(List.of(new GameView()));
        final List<CommandEventListener> commandViews = new ArrayList<>(List.of(new CommandView()));
        final List<PromptEventListener> promptViews = new ArrayList<>(List.of(new PromptView()));

        final PromptController promptController = new PromptController(mainView, gameSaveView,
                characterView, promptYesNoValidation, promptViews);

        final GameTime gameTime = new GameTime();
        final Game game = new Game(mainView, characterView, commandViews,
                                    gameSerialization, gameTime, gameObservers);
        final var randomCommonEnemyGenerator = new RandomCommonEnemyGenerator(enemyObservers);
        final var randomItemContainerGenerator = new RandomItemContainerGenerator(itemObservers);

        try {

            PlayerCharacter character;
            GameMap map;
            String saveName = "";
            long startTime;

            boolean startNewGame = promptController.promptNewGame();

            if (startNewGame) {
                saveName = promptController.promptNewSaveName();
                map = new GameMap(enemyObservers, randomCommonEnemyGenerator, randomItemContainerGenerator);
                String characterType = promptController.promptCharacterType();
                character = new PlayerCharacter(characterType, characterObservers, placeObservers);
                character.findCharacterAndSetCoordinates(map);
                startTime = System.currentTimeMillis();
                gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTime);
                mainView.outputln("New save \"" + saveName + "\" created successfully!");

                commandViews.forEach(CommandEventListener::showHelpCommands);
            } else {
                saveName = promptController.promptLoadGame();
                DeserializedBundle deserializedBundle = gameSerialization.readFromSave(
                        saveName, characterObservers, placeObservers, itemObservers, enemyObservers
                );
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
                    new CollectXPForFortress(character, map.getFortress()),
                    new DefeatTheGoblinKing(won, map.getFortress())
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
