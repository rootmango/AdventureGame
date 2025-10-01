package commands;

import playercharacter.Direction;

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

    @Override
    public void execute() {
        if (args.length == 0) {
            commandObserver.requestedHelp();
        } else {
            synchronized (lock) {
                if (checkUp(args[0])) {
                    commandObserver.movedNorth();
                    character.move(map, Direction.UP);
                } else if (checkLeft(args[0])) {
                    commandObserver.movedWest();
                    character.move(map, Direction.LEFT);
                } else if (checkRight(args[0])) {
                    commandObserver.movedEast();
                    character.move(map, Direction.RIGHT);
                } else if (checkDown(args[0])) {
                    commandObserver.movedSouth();
                    character.move(map, Direction.DOWN);
                }
            }
        }
    }
}
