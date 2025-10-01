package mvc.observers;

import mvc.views.PromptView;

public class PromptObserver {
    protected final PromptView promptView;

    public PromptObserver(PromptView promptView) {
        this.promptView = promptView;
    }

    public void promptedStartNewGame() {
        promptView.askStartNewGame();
    }

    public void promptedStartNewGameWithHint() {
        promptView.askStartNewGameWithHint();
    }

    public void noExistingSaves() {
        promptView.showNoExistingSavesMessage();
    }

    public void promptedChooseSave() {
        promptView.showChooseSaveMessage();
    }

    public void noSuchSave() {
        promptView.showNoSuchSaveMessage();
    }

    public void promptedSaveName() {
        promptView.askSaveName();
    }

    public void enteredInvalidSaveNameIllegalChars() {
        promptView.showInvalidSaveNameIllegalCharsMessage();
    }

    public void enteredInvalidSaveNameTaken() {
        promptView.showInvalidSaveNameTakenMessage();
    }

    public void promptedCharacterType() {
        promptView.askCharacterType();
    }

    public void enteredInvalidCharacterType() {
        promptView.showInvalidCharacterTypeMessage();
    }
}
