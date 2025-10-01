package commands;

import java.util.NoSuchElementException;

public class UseCommand extends Command {
    public UseCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            commandObserver.requestedHelp();
        } else {
            synchronized (lock) {
                String itemName = getSubjectNameFromCommandArgs(args);
                try {
                    character.useItemByName(itemName);
                } catch (NoSuchElementException e) {
                    characterView.showNoSuchItemInInventoryMessage();
                }
            }
        }
    }
}
