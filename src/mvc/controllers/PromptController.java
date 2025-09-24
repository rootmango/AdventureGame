package mvc.controllers;

import gameio.GameSerialization;
import mvc.views.CharacterView;
import mvc.views.GameSaveView;
import mvc.views.MainView;
import mvc.views.PromptYesNoView;

import java.io.IOException;
import java.util.List;

public class PromptController {

    protected final MainView mainView;
    protected final GameSaveView gameSaveView;
    protected final CharacterView characterView;
    protected final PromptYesNoView promptYesNoView;

    public PromptController(MainView mainView, GameSaveView gameSaveView, CharacterView characterView,
                            PromptYesNoView promptYesNoView) {
        this.mainView = mainView;
        this.gameSaveView = gameSaveView;
        this.characterView = characterView;
        this.promptYesNoView = promptYesNoView;
    }

    /**
     * Prompts for whether a new game should be started and returns the answer.
     */
    public boolean promptNewGame() throws IOException {
        boolean isValidInput = false;
        String answer = "";
        mainView.outputln("Do you want to start a new game?");
        while (!isValidInput) {
            mainView.output("> ");
            answer = mainView.userInputString();
            isValidInput = promptYesNoView.isValidInput(answer);
            if (!isValidInput) {
                mainView.outputln("Do you want to start a new game? (yes/no)");
            }
        }

        if (promptYesNoView.isYes(answer)) {
            return true;
        } else {
            // if we are out of the while loop, then answer is necessarily in one of
            // promptYesNoView's two sets of either "yes" or "no" answers.
            // Since we're in the else block (answer is not in the "yes" set),
            // answer is in the "no" set.
            if (gameSaveView.savesDirIsEmpty()) {
                mainView.outputln("No existing saves to load from. Creating a new game instead.");
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
            mainView.outputln("Choose a save: ");
            mainView.output("> ");
            answer = mainView.userInputString();
            if (!availableSavesNames.contains(answer)) {
                mainView.outputln("No save with such name!");
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
        mainView.outputln("Enter save name:");
        while (saveName.isEmpty()) {
            mainView.output("> ");
            String input = mainView.userInputString();
            boolean isValidInput = gameSaveView.isValidNewSaveName(input);
            boolean isTakenSaveName = gameSaveView.saveNameIsTaken(input);
            if (!isValidInput) {
                mainView.outputln("Invalid save name! Try again! (Must not contain illegal characters)");
            } else if (isTakenSaveName) {
                mainView.outputln("Invalid save name! Try again! (Save name is already taken)");
            } else {
                saveName = input;
            }
        }

        return saveName;
    }

    public String promptCharacterType() {
        mainView.outputln("Choose a class for your character!");
        characterView.showCharacterTypeNames();
        String answer = "";
        boolean isValidCharacterTypeName = false;
        while (!isValidCharacterTypeName) {
            mainView.output("> ");
            answer = mainView.userInputString();
            isValidCharacterTypeName = characterView.isValidCharacterTypeName(answer);
            if (!isValidCharacterTypeName) {
                mainView.outputln("Invalid name! Try again!");
            }
        }
        characterView.showOnChosenCharacterType(answer);
        return answer;
    }
}
