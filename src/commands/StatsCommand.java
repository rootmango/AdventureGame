package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class StatsCommand extends Command {
    public StatsCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public StatsCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        characterView.showCharacterStats(character);
    }
}
