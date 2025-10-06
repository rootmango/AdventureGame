package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class InventoryCommand extends Command {
    public InventoryCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public InventoryCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.showInventory(character);
    }
}
