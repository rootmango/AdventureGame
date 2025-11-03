package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class InventoryCommand extends Command {
    public InventoryCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        characterView.showInventory(character);
    }
}
