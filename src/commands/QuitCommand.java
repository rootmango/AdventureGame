package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class QuitCommand extends Command {
    public QuitCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            quit.setValue(true);
        }
    }
}
