package mvc.controllers;

import mvc.observers.PromptObserver;
import mvc.views.CharacterView;
import mvc.views.GameSaveView;
import mvc.views.MainView;
import mvc.views.PromptYesNoValidation;

import java.io.IOException;
import java.util.List;

public class PromptController {

    protected final MainView mainView;
    protected final GameSaveView gameSaveView;
    protected final CharacterView characterView;
    protected final PromptYesNoValidation promptYesNoValidation;
    protected final PromptObserver promptObserver;

    public PromptController(MainView mainView, GameSaveView gameSaveView, CharacterView characterView,
                            PromptYesNoValidation promptYesNoValidation, PromptObserver promptObserver) {
        this.mainView = mainView;
        this.gameSaveView = gameSaveView;
        this.characterView = characterView;
        this.promptYesNoValidation = promptYesNoValidation;
        this.promptObserver = promptObserver;
    }

    /**
     * Prompts for whether a new game should be started and returns the answer.
     */
    public boolean promptNewGame() throws IOException {
        boolean isValidInput = false;
        String answer = "";
        promptObserver.promptedStartNewGame();
        while (!isValidInput) {
            answer = mainView.userInputString();
            isValidInput = promptYesNoValidation.isValidInput(answer);
            if (!isValidInput) {
                promptObserver.promptedStartNewGameWithHint();
            }
        }

        if (promptYesNoValidation.isYes(answer)) {
            return true;
        } else {
            // if we are out of the while loop, then answer is necessarily in one of
            // promptYesNoView's two sets of either "yes" or "no" answers.
            // Since we're in the else block (answer is not in the "yes" set),
            // answer is in the "no" set.
            if (gameSaveView.savesDirIsEmpty()) {
                promptObserver.noExistingSaves();
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Prompts for a save name to load and returns the answer.
     */
    public String promptLoadGame() throws IOException {
        List<String> availableSavesNames = gameSaveView.showAvailableSavesNames();
        String answer = "";
        while (!availableSavesNames.contains(answer)) {
            promptObserver.promptedChooseSave();
            answer = mainView.userInputString();
            if (!availableSavesNames.contains(answer)) {
                promptObserver.noSuchSave();
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
    public String promptNewSaveName() throws IOException {
        String saveName = "";
        promptObserver.promptedSaveName();
        while (saveName.isEmpty()) {
            String input = mainView.userInputString();
            boolean isValidInput = gameSaveView.isValidNewSaveName(input);
            boolean isTakenSaveName = gameSaveView.saveNameIsTaken(input);
            if (!isValidInput) {
                promptObserver.enteredInvalidSaveNameIllegalChars();
            } else if (isTakenSaveName) {
                promptObserver.enteredInvalidSaveNameTaken();
            } else {
                saveName = input;
            }
        }

        return saveName;
    }

    public String promptCharacterType() {
        promptObserver.promptedCharacterType();
        characterView.showCharacterTypeNames();
        String answer = "";
        boolean isValidCharacterTypeName = false;
        while (!isValidCharacterTypeName) {
            answer = mainView.userInputString();
            isValidCharacterTypeName = characterView.isValidCharacterTypeName(answer);
            if (!isValidCharacterTypeName) {
                promptObserver.enteredInvalidCharacterType();
            }
        }
        characterView.showOnChosenCharacterType(answer);
        return answer;
    }
}
