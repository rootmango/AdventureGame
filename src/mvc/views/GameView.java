package mvc.views;

import game.GameTime;
import playercharacter.PlayerCharacter;

import java.io.IOException;

public class GameView extends MainView {
    protected final GameTime gameTime = new GameTime();

    public void showQuitMessages(PlayerCharacter character, CharacterView characterView) {
        outputln("Quitting game...");
        outputln("\nCharacter stats:");
        characterView.showCharacterStats(character);
        outputln();
    }

    public void showWonMessages(PlayerCharacter character, CharacterView characterView,
                                long startTime) {
        outputln("You have won the game!");
        outputln("\nCharacter stats:");
        characterView.showCharacterStats(character);
        outputln();
        gameTime.printElapsedTime(startTime, this);
    }

    public void showDiedMessages(long startTime) {
        outputln("You have died!");
        gameTime.printElapsedTime(startTime, this);
    }

    public void showErrorDeletingSaveMessage(Exception e) {
        outputln("Couldn't delete save: " + e.getMessage());
    }

    public void showIOErrorMessage(IOException e) {
        outputln("Error: " + e.getMessage());
    }
}
