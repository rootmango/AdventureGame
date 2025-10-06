package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class EquippedCommand extends Command{
    public EquippedCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public EquippedCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.onAlreadyEquippedItem(character);
    }
}
