package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class StatsCommand extends Command {
    public StatsCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        characterView.showCharacterStats(character);
    }
}
