package game;

import gameio.GameSerialization;
import mvc.View;
import playercharacter.PlayerCharacter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class PromptHandler {

    private static final View view = new View();

    /**
     * Prompts for whether a new game should be started and returns the answer.
     */
    public boolean promptNewGame() throws IOException {
        Set<String> yesValues = Set.of("yes", "y", "yeah", "ok", "okay", "sure");
        Set<String> noValues = Set.of("no", "n");
        boolean isValidInput = false;
        String answer = "";
        view.outputln("Do you want to start a new game?");
        while (!isValidInput) {
            view.output("> ");
            answer = view.userInputString().toLowerCase();
            isValidInput = yesValues.contains(answer) || noValues.contains(answer);
            if (!isValidInput) {
                view.outputln("Do you want to start a new game? (yes/no)");
            }
        }

        if (yesValues.contains(answer)) {
            return true;
        } else { // if we are out of the while loop, then answer is necessarily in one of the two sets
            if (GameSerialization.savesDirIsEmpty()) {
                view.outputln("No existing saves to load from. Creating a new game instead.");
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
        List<String> availableSavesNames = GameSerialization.avaiableSavesNames();
        view.outputln("Available saves: ");
        availableSavesNames.forEach(System.out::println);
        String answer = "";
        while (!availableSavesNames.contains(answer)) {
            view.outputln("Choose a save: ");
            view.output("> ");
            answer = view.userInputString();
            if (!availableSavesNames.contains(answer)) {
                view.outputln("No save with such name!");
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
        view.outputln("Enter save name:");
        while (saveName.isEmpty()) {
            view.output("> ");
            String input = view.userInputString();
            boolean isValidInput = input.chars()
                    .filter(c -> !Character.isLetterOrDigit(c))
                    .filter(c -> !Character.isSpaceChar(c))
                    .findAny()
                    .isEmpty();
                    // removes all letters, numbers and spaces from input.
                    // if the result isn't empty, then the input string contains
                    // illegal characters.
            if (input.isBlank() || !isValidInput) {
                view.outputln("Invalid save name! Try again! (Must not contain illegal characters)");
            } else if (GameSerialization.avaiableSavesNames().contains(input)) {
                view.outputln("Invalid save name! Try again! (Save name is already taken)");
            } else {
                saveName = input.strip();
            }
        }

        return saveName;
    }

    public String promptCharacterType() {
        view.outputln("Choose a class for your character!");
        view.outputln("- " + PlayerCharacter.characterTypes.get("warrior").description());
        view.outputln("- " + PlayerCharacter.characterTypes.get("mage").description());
        view.outputln("- " + PlayerCharacter.characterTypes.get("rogue").description());
        String answer = "";
        Set<String> validAnswers = PlayerCharacter.characterTypes.keySet();
        while (!validAnswers.contains(answer.toLowerCase())) {
            view.output("> ");
            answer = view.userInputString().toLowerCase().strip();
            if (!validAnswers.contains(answer.toLowerCase())) {
                view.outputln("Invalid name! Try again!");
            }
        }
        view.outputln(PlayerCharacter.characterTypes.get(answer).onChosen());
        return answer;
    }
}
