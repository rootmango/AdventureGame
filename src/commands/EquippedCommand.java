package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class EquippedCommand extends Command{
    public EquippedCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        characterView.onAlreadyEquippedItem(character);
    }
}
