package commands;

import gameexceptions.InsufficientCommandArgsException;
import maps.Place;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;
import java.util.NoSuchElementException;

public class TakeCommand extends Command{
    public TakeCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
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
                commandEventListener.showNoSuchItemContainerMessage();
            }
        }
    }
}
