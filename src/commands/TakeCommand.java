package commands;

import gameexceptions.InsufficientCommandArgsException;
import gameexceptions.InvalidCommandArgsException;
import maps.Place;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;
import java.util.NoSuchElementException;

public class TakeCommand extends Command{
    public TakeCommand(CommandParameters commandParams) {
        super(commandParams);
        validateArgs();
    }

    public TakeCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
        validateArgs();
    }

    protected void validateArgs() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        }
    }

    @Override
    public void execute() {
        synchronized (lock) {
            String itemContainerName = getSubjectNameFromCommandArgs(args);
            Place currentPlace = character.getCurrentPlace(map);
            try {
                character.takeItemByName(currentPlace, itemContainerName);
            } catch (NoSuchElementException e) {
                commandEventListeners.forEach(CommandEventListener::showNoSuchItemContainerMessage);
            }
        }
    }
}
