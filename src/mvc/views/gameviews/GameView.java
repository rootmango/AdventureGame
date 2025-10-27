package mvc.views.gameviews;

import game.GameTime;
import mvc.views.MainView;
import mvc.views.characterviews.CharacterView;
import playercharacter.PlayerCharacter;

import java.io.IOException;

public class GameView extends MainView implements GameObserver {
    protected final GameTime gameTime = new GameTime();

    public void onQuit(PlayerCharacter character, CharacterView characterView) {
        outputln("Quitting game...");
        outputln("\nCharacter stats:");
        characterView.showCharacterStats(character);
        outputln();
    }

    public void onWon(PlayerCharacter character, CharacterView characterView,
                      long startTime) {
        outputln("You have won the game!");
        outputln("\nCharacter stats:");
        characterView.showCharacterStats(character);
        outputln();
        gameTime.printElapsedTime(startTime, this);
    }

    public void onDied(long startTime) {
        outputln("You have died!");
        gameTime.printElapsedTime(startTime, this);
    }

    public void onErrorDeletingSave(Exception e) {
        outputln("Couldn't delete save: " + e.getMessage());
    }

    public void onIOError(IOException e) {
        outputln("Error: " + e.getMessage());
    }
}
