package mvc.views;

public class PromptView extends MainView{
    public void showPromptChar() {
        output("> ");
    }

    public void askStartNewGame() {
        outputln("Do you want to start a new game?");
        showPromptChar();
    }

    public void askStartNewGameWithHint() {
        outputln("Do you want to start a new game? (yes/no)");
        showPromptChar();
    }

    public void showNoExistingSavesMessage() {
        outputln("No existing saves to load from. Creating a new game instead.");
    }

    public void showChooseSaveMessage() {
        outputln("Choose a save: ");
        showPromptChar();
    }

    public void showNoSuchSaveMessage() {
        outputln("No save with such name!");
        showPromptChar();
    }

    public void askSaveName() {
        outputln("Enter save name:");
        showPromptChar();
    }

    public void showInvalidSaveNameIllegalCharsMessage() {
        outputln("Invalid save name! Try again! (Must not contain illegal characters)");
        showPromptChar();
    }

    public void showInvalidSaveNameTakenMessage() {
        outputln("Invalid save name! Try again! (Save name is already taken)");
        showPromptChar();
    }

    public void askCharacterType() {
        outputln("Choose a class for your character!");
    }

    public void showInvalidCharacterTypeMessage() {
        outputln("Invalid name! Try again!");
        showPromptChar();
    }
}
