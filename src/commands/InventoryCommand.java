package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class InventoryCommand extends Command {
    public InventoryCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public InventoryCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.showInventory(character);
    }
}
