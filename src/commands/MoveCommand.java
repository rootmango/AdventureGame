package commands;

import gameexceptions.InsufficientCommandArgsException;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;
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
    }

    public MoveCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        } else {
            synchronized (lock) {
                if (checkUp(args[0])) {
                    commandViews.forEach(CommandViewInterface::showMoveNorthMessage);
                    character.move(map, Direction.UP);
                } else if (checkLeft(args[0])) {
                    commandViews.forEach(CommandViewInterface::showMoveWestMessage);
                    character.move(map, Direction.LEFT);
                } else if (checkRight(args[0])) {
                    commandViews.forEach(CommandViewInterface::showMoveEastMessage);
                    character.move(map, Direction.RIGHT);
                } else if (checkDown(args[0])) {
                    commandViews.forEach(CommandViewInterface::showMoveSouthMessage);
                    character.move(map, Direction.DOWN);
                }
            }
        }
    }
}
