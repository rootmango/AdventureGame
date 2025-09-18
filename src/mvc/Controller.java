package mvc;

import java.util.Arrays;
import java.util.Set;

public class Controller {
    private static final Set<String> MOVE_UP_VALUES = Set.of(
            "north"
//            "up",
//            "w"
    );
    private static final Set<String> MOVE_LEFT_VALUES = Set.of(
            "west"
//            "left",
//            "a"
    );
    private static final Set<String> MOVE_RIGHT_VALUES = Set.of(
            "east"
//            "right",
//            "d"
    );
    private static final Set<String> MOVE_DOWN_VALUES = Set.of(
            "south"
//            "down",
//            "s"
    );

    public boolean checkUp(String s) {
        return MOVE_UP_VALUES.contains(s.toLowerCase());
    }

    public boolean checkLeft(String s) {
        return MOVE_LEFT_VALUES.contains(s.toLowerCase());
    }

    public boolean checkRight(String s) {
        return MOVE_RIGHT_VALUES.contains(s.toLowerCase());
    }

    public boolean checkDown(String s) {
        return MOVE_DOWN_VALUES.contains(s.toLowerCase());
    }

    public String getCommandFromInputArgs(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return "";
        } else {
            return inputArgs[0];
        }
    }

    public String[] getCommandArgsFromInputArgs(String[] inputArgs) {
        return Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
    }

    public String getSubjectNameFromCommandArgs(String[] commandArgs) {
        return String.join(" ", Arrays.copyOfRange(commandArgs, 0, commandArgs.length));
        // joins together the rest of the line in case of multi-word names
        // so that, if the command is "attack goblin king", the method will return "goblin king"
    }
}
