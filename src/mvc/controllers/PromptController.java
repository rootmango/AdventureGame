package mvc.controllers;

import mvc.controllers.game.CoreGameStateBundle;
import mvc.controllers.game.GameTimeUtils;
import gameio.DeserializedBundle;
import gameio.GameSerialization;
import gamerandom.RandomCommonEnemyGenerator;
import gamerandom.RandomItemContainerGenerator;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterObserver;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandView;
import mvc.views.enemyviews.EnemyObserver;
import mvc.views.gameviews.GameCLIViews;
import mvc.views.itemviews.ItemObserver;
import mvc.views.placeviews.PlaceObserver;
import mvc.views.promptviews.PromptView;
import playercharacter.PlayerCharacter;

import java.io.IOException;
import java.util.List;

public class PromptController {

    protected final PromptView promptView;
    protected final GameSaveView gameSaveView;
    protected final CharacterView characterView;
    protected final PromptYesNoValidation promptYesNoValidation;

    public PromptController(GameCLIViews gameCLIViews) {
        this.promptView = gameCLIViews.promptView();
        this.gameSaveView = gameCLIViews.gameSaveView();
        this.characterView = gameCLIViews.characterView();
        this.promptYesNoValidation = gameCLIViews.promptYesNoValidation();
    }

    public CoreGameStateBundle promptInitializeGameState(GameCLIViews gameCLIViews,
                                                         GameSerialization gameSerialization)
            throws IOException {
        PlayerCharacter character;
        GameMap map;
        String saveName;
        long startTime;


        MainView mainView = gameCLIViews.mainView();

        List<CharacterObserver> characterObservers = gameCLIViews.characterObservers();
        List<PlaceObserver> placeObservers = gameCLIViews.placeObservers();
        List<EnemyObserver> enemyObservers = gameCLIViews.enemyObservers();
        List<ItemObserver> itemObservers = gameCLIViews.itemObservers();
        var randomCommonEnemyGenerator = new RandomCommonEnemyGenerator(enemyObservers);
        var randomItemContainerGenerator = new RandomItemContainerGenerator(itemObservers);

        GameTimeUtils gameTimeUtils = new GameTimeUtils();

        boolean startNewGame = promptNewGame();

        if (startNewGame) {
            saveName = promptNewSaveName();
            map = new GameMap(enemyObservers, randomCommonEnemyGenerator, randomItemContainerGenerator);
            String characterType = promptCharacterType();
            character = new PlayerCharacter(characterType, characterObservers, placeObservers);
            character.findCharacterAndSetCoordinates(map);
            startTime = System.currentTimeMillis();
            gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTimeUtils);
            mainView.outputln("New save \"" + saveName + "\" created successfully!");

            new CommandView().showHelpCommands();
        } else {
            saveName = promptLoadGame();
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

        return new CoreGameStateBundle(character, map, saveName, startTime);
    }

    /**
     * Prompts for whether a new mvc.controllers.game should be started and returns the answer.
     */
    protected boolean promptNewGame() throws IOException {
        if (gameSaveView.savesDirIsEmpty()) {
            promptView.showNoExistingSavesMessage();
            return true;
        } else {
            boolean isValidInput = false;
            String answer = "";
            promptView.askStartNewGame();
            while (!isValidInput) {
                answer = promptView.userInputString();
                isValidInput = promptYesNoValidation.isValidInput(answer);
                if (!isValidInput) {
                    promptView.askStartNewGameWithHint();
                }
            }

            if (promptYesNoValidation.isYes(answer)) {
                return true;
            } else {
                // if we are out of the while loop, then answer is necessarily in one of
                // promptYesNoView's two sets of either "yes" or "no" answers.
                // Since we're in the else block (answer is not in the "yes" set),
                // answer is in the "no" set.
                return false;
            }
        }
    }

    /**
     * Prompts for a save name to load and returns the answer.
     */
    protected String promptLoadGame() throws IOException {
        List<String> availableSavesNames = gameSaveView.showAvailableSavesNames();
        String answer = "";
        while (!availableSavesNames.contains(answer)) {
            promptView.showChooseSaveMessage();
            answer = promptView.userInputString();
            if (!availableSavesNames.contains(answer)) {
                promptView.showNoSuchSaveMessage();
            }
        }

        if (availableSavesNames.contains(answer)) {
            return answer;
        } else {
            throw new RuntimeException();
            // theoretically should never happen, since if we are out of the while loop,
            // availableSavesNames will always contain answer
        }
    }

    /**
     * Prompts for a name for a new save and returns it.
     */
    protected String promptNewSaveName() throws IOException {
        String saveName = "";
        promptView.askSaveName();
        while (saveName.isEmpty()) {
            String input = promptView.userInputString();
            boolean isValidInput = gameSaveView.isValidNewSaveName(input);
            boolean isTakenSaveName = gameSaveView.saveNameIsTaken(input);
            if (!isValidInput) {
                promptView.showInvalidSaveNameIllegalCharsMessage();
            } else if (isTakenSaveName) {
                promptView.showInvalidSaveNameTakenMessage();
            } else {
                saveName = input;
            }
        }

        return saveName;
    }

    protected String promptCharacterType() {
        promptView.askCharacterType();
        characterView.showCharacterTypeNames();
        String answer = "";
        boolean isValidCharacterTypeName = false;
        while (!isValidCharacterTypeName) {
            answer = promptView.userInputString();
            isValidCharacterTypeName = characterView.isValidCharacterTypeName(answer);
            if (!isValidCharacterTypeName) {
                promptView.showInvalidCharacterTypeMessage();
            }
        }
        characterView.showOnChosenCharacterType(answer);
        return answer;
    }
}
