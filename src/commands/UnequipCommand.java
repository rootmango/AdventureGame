package commands;

import gameexceptions.NoEquippedItemException;

public class UnequipCommand extends Command {
    public UnequipCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            try {
                character.unequip();
            } catch (NoEquippedItemException e) {
                characterView.showNoSuchItemCurrentlyEquippedMessage();
            }
        }
    }
}
