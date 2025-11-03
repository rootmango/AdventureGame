package commands;

import gameexceptions.InsufficientCommandArgsException;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;
import java.util.NoSuchElementException;

public class UseCommand extends Command {
    public UseCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
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
            String itemName = getSubjectNameFromCommandArgs(args);
            try {
                character.useItemByName(itemName);
            } catch (NoSuchElementException e) {
                characterView.onNoSuchItemInInventory();
            }
        }
    }
}
