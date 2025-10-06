package mvc.controllers;

import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.promptviews.PromptView;
import mvc.views.promptviews.PromptViewInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PromptController {

    protected final MainView mainView;
    protected final GameSaveView gameSaveView;
    protected final CharacterView characterView;
    protected final PromptYesNoValidation promptYesNoValidation;
    protected final List<PromptViewInterface> promptViews = new ArrayList<>();

    public void addPromptViews(List<PromptViewInterface> promptViews) {
        this.promptViews.addAll(promptViews);
    }

    public PromptController(MainView mainView, GameSaveView gameSaveView, CharacterView characterView,
                            PromptYesNoValidation promptYesNoValidation) {
        this.mainView = mainView;
        this.gameSaveView = gameSaveView;
        this.characterView = characterView;
        this.promptYesNoValidation = promptYesNoValidation;
    }

    public PromptController(MainView mainView, GameSaveView gameSaveView, CharacterView characterView,
                            PromptYesNoValidation promptYesNoValidation, List<PromptViewInterface> promptViews) {
        this(mainView, gameSaveView, characterView, promptYesNoValidation);
        this.promptViews.addAll(promptViews);
    }

    /**
     * Prompts for whether a new game should be started and returns the answer.
     */
    public boolean promptNewGame() throws IOException {


        /*if (promptYesNoValidation.isYes(answer)) {
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
        }*/

        if (gameSaveView.savesDirIsEmpty()) {
            promptViews.forEach(PromptViewInterface::showNoExistingSavesMessage);
            return true;
        } else {
            boolean isValidInput = false;
            String answer = "";
            promptViews.forEach(PromptViewInterface::askStartNewGame);
            while (!isValidInput) {
                answer = mainView.userInputString();
                isValidInput = promptYesNoValidation.isValidInput(answer);
                if (!isValidInput) {
                    promptViews.forEach(PromptViewInterface::askStartNewGameWithHint);
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
    public String promptLoadGame() throws IOException {
        List<String> availableSavesNames = gameSaveView.showAvailableSavesNames();
        String answer = "";
        while (!availableSavesNames.contains(answer)) {
            promptViews.forEach(PromptViewInterface::showChooseSaveMessage);
            answer = mainView.userInputString();
            if (!availableSavesNames.contains(answer)) {
                promptViews.forEach(PromptViewInterface::showNoSuchSaveMessage);
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
        promptViews.forEach(PromptViewInterface::askSaveName);
        while (saveName.isEmpty()) {
            String input = mainView.userInputString();
            boolean isValidInput = gameSaveView.isValidNewSaveName(input);
            boolean isTakenSaveName = gameSaveView.saveNameIsTaken(input);
            if (!isValidInput) {
                promptViews.forEach(PromptViewInterface::showInvalidSaveNameIllegalCharsMessage);
            } else if (isTakenSaveName) {
                promptViews.forEach(PromptViewInterface::showInvalidSaveNameTakenMessage);
            } else {
                saveName = input;
            }
        }

        return saveName;
    }

    public String promptCharacterType() {
        promptViews.forEach(PromptViewInterface::askCharacterType);
        characterView.showCharacterTypeNames();
        String answer = "";
        boolean isValidCharacterTypeName = false;
        while (!isValidCharacterTypeName) {
            answer = mainView.userInputString();
            isValidCharacterTypeName = characterView.isValidCharacterTypeName(answer);
            if (!isValidCharacterTypeName) {
                promptViews.forEach(PromptViewInterface::showInvalidCharacterTypeMessage);
            }
        }
        characterView.showOnChosenCharacterType(answer);
        return answer;
    }
}
