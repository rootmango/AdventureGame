package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class EquippedCommand extends Command{
    public EquippedCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public EquippedCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.onAlreadyEquippedItem(character);
    }
}
