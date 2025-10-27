package commands;

import gameexceptions.InaccessiblePlaceException;
import gameexceptions.InsufficientCommandArgsException;
import gameexceptions.InvalidCommandArgsException;
import mvc.views.commandviews.CommandEventListener;
import playercharacter.Direction;

import java.util.List;
import java.util.Set;

public class MoveCommand extends Command {
    protected final Set<String> MOVE_UP_VALUES = Set.of(
            "north"
//            "up",
//            "w"
    );
    protected final Set<String> MOVE_LEFT_VALUES = Set.of(
            "west"
//            "left",
//            "a"
    );
    protected final Set<String> MOVE_RIGHT_VALUES = Set.of(
            "east"
//            "right",
//            "d"
    );
    protected final Set<String> MOVE_DOWN_VALUES = Set.of(
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

    public MoveCommand(CommandParameters commandParams) {
        super(commandParams);
        validateArgs();
    }

    public MoveCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
        validateArgs();
    }

    protected void validateArgs() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        } else if (!checkUp(args[0]) && !checkLeft(args[0])
                && !checkRight(args[0]) && !checkDown(args[0])) {
            throw new InvalidCommandArgsException("Invalid direction! " +
                    "The valid directions are \"north\", \"south\", \"east\" and \"west\"");
        }
    }

    @Override
    public void execute() {
        synchronized (lock) {
            try {
                if (checkUp(args[0])) {
                    commandEventListeners.forEach(CommandEventListener::showMoveNorthMessage);
                    character.move(map, Direction.UP);
                } else if (checkLeft(args[0])) {
                    commandEventListeners.forEach(CommandEventListener::showMoveWestMessage);
                    character.move(map, Direction.LEFT);
                } else if (checkRight(args[0])) {
                    commandEventListeners.forEach(CommandEventListener::showMoveEastMessage);
                    character.move(map, Direction.RIGHT);
                } else if (checkDown(args[0])) {
                    commandEventListeners.forEach(CommandEventListener::showMoveSouthMessage);
                    character.move(map, Direction.DOWN);
                }
            } catch (InaccessiblePlaceException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
