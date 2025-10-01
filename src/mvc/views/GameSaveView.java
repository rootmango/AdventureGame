package mvc.views;

import gameio.GameSerialization;

import java.io.IOException;
import java.util.List;

public class GameSaveView extends MainView {
    protected final GameSerialization gameSerialization;

    public GameSaveView(GameSerialization gameSerialization) {
        this.gameSerialization = gameSerialization;
    }

    public List<String> showAvailableSavesNames() throws IOException {
        List<String> availableSavesNames = gameSerialization.avaiableSavesNames();
        outputln("Available saves: ");
        availableSavesNames.forEach(System.out::println);
        return availableSavesNames;
    }

    public boolean isValidNewSaveName(String name) {
        boolean isValidInput = name.strip().chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .filter(c -> !Character.isSpaceChar(c))
                .findAny()
                .isEmpty();
        // removes all letters, numbers and spaces from input.
        // if the result isn't empty, then the input string contains
        // illegal characters.

        return !name.isBlank() && isValidInput;
    }

    public boolean saveNameIsTaken(String name) throws IOException {
        return gameSerialization.avaiableSavesNames().contains(name);
    }

    public boolean savesDirIsEmpty() throws IOException {
        return gameSerialization.savesDirIsEmpty();
    }
}
