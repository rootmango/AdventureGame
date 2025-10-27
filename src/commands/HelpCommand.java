package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class HelpCommand extends Command{
    public HelpCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public HelpCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        commandEventListeners.forEach(CommandEventListener::showHelpCommands);
    }
}
