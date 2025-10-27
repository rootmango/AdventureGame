package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class StatsCommand extends Command {
    public StatsCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public StatsCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.showCharacterStats(character);
    }
}
