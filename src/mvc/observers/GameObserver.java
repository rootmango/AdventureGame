package mvc.observers;

import mvc.views.CharacterView;
import mvc.views.GameView;
import playercharacter.PlayerCharacter;

import java.io.IOException;

public class GameObserver {
    protected final GameView gameView;

    public GameObserver(GameView gameView) {
        this.gameView = gameView;
    }

    public void quitGame(PlayerCharacter character, CharacterView characterView) {
        gameView.showQuitMessages(character, characterView);
    }

    public void won(PlayerCharacter character, CharacterView characterView, long startTime) {
        gameView.showWonMessages(character, characterView, startTime);
    }

    public void died(long startTime) {
        gameView.showDiedMessages(startTime);
    }

    public void errorDeletingSave(Exception e) {
        gameView.showErrorDeletingSaveMessage(e);
    }

    public void generalIOError(IOException e) {
        gameView.showIOErrorMessage(e);
    }
}
