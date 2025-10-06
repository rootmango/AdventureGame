package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class HelpCommand extends Command{
    public HelpCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public HelpCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        commandViews.forEach(CommandViewInterface::showHelpCommands);
    }
}
