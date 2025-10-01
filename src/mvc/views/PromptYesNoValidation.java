package mvc.views;

import java.util.Set;

public class PromptYesNoValidation {
    public Set<String> yesValues = Set.of("yes", "y", "yeah", "ok", "okay", "sure");
    public Set<String> noValues = Set.of("no", "n");

    public boolean isValidInput(String input) {
        return yesValues.contains(input.toLowerCase().strip())
                || noValues.contains(input.toLowerCase().strip());
    }

    public boolean isYes(String input) {
        return yesValues.contains(input.toLowerCase().strip());
    }

    public boolean isNo(String input) {
        return noValues.contains(input.toLowerCase().strip());
    }
}
