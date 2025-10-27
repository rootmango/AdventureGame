package mvc.views.promptviews;

public interface PromptEventListener {
    void showPromptChar();

    void askStartNewGame();

    void askStartNewGameWithHint();

    void showNoExistingSavesMessage();

    void showChooseSaveMessage();

    void showNoSuchSaveMessage();

    void askSaveName();

    void showInvalidSaveNameIllegalCharsMessage();

    void showInvalidSaveNameTakenMessage();

    void askCharacterType();

    void showInvalidCharacterTypeMessage();
}
