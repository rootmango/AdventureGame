package mvc.views.gameviews;

import mvc.views.characterviews.CharacterView;
import playercharacter.PlayerCharacter;

import java.io.IOException;

public interface GameViewInterface {
    void onQuit(PlayerCharacter character, CharacterView characterView);

    void onWon(PlayerCharacter character, CharacterView characterView, long startTime);

    void onDied(long startTime);

    void onErrorDeletingSave(Exception e);

    void onIOError(IOException e);
}
