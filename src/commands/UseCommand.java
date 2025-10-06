package commands;

import gameexceptions.InsufficientCommandArgsException;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;
import java.util.NoSuchElementException;

public class UseCommand extends Command {
    public UseCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public UseCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            throw new InsufficientCommandArgsException();
        } else {
            synchronized (lock) {
                String itemName = getSubjectNameFromCommandArgs(args);
                try {
                    character.useItemByName(itemName);
                } catch (NoSuchElementException e) {
                    characterView.onNoSuchItemInInventory();
                }
            }
        }
    }
}
