package commands;

import gameexceptions.InsufficientCommandArgsException;
import maps.Place;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;
import java.util.NoSuchElementException;

public class TakeCommand extends Command{
    public TakeCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public TakeCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        } else {
            synchronized (lock) {
                String itemContainerName = getSubjectNameFromCommandArgs(args);
                Place currentPlace = character.getCurrentPlace(map);
                try {
                    character.takeItemByName(currentPlace, itemContainerName);
                } catch (NoSuchElementException e) {
                    commandViews.forEach(CommandViewInterface::showNoSuchItemContainerMessage);
                }
            }
        }
    }
}
