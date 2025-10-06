package commands;

import gameexceptions.NoEquippedItemException;
import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class UnequipCommand extends Command {
    public UnequipCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public UnequipCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
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
