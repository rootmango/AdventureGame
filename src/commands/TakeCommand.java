package commands;

import maps.Place;

import java.util.NoSuchElementException;

public class TakeCommand extends Command{
    public TakeCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        if (args.length == 0) {
            commandObserver.requestedHelp();
        } else {
            synchronized (lock) {
                String itemContainerName = getSubjectNameFromCommandArgs(args);
                Place currentPlace = character.getCurrentPlace(map);
                try {
                    character.takeItemByName(currentPlace, itemContainerName);
                } catch (NoSuchElementException e) {
                    commandObserver.requestedNonExistingItemContainer();
                }
            }
        }
    }
}
