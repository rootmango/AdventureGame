package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class QuitCommand extends Command {
    public QuitCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public QuitCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            quit.setValue(true);
        }
    }
}
