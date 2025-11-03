package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class HelpCommand extends Command{
    public HelpCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        commandEventListener.showHelpCommands();
    }
}
