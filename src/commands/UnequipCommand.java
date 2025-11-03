package commands;

import gameexceptions.NoEquippedItemException;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class UnequipCommand extends Command {
    public UnequipCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            try {
                character.unequip();
            } catch (NoEquippedItemException e) {
                characterView.onNoSuchItemCurrentlyEquipped();
            }
        }
    }
}
