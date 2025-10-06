package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class QuitCommand extends Command {
    public QuitCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public QuitCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            quit.setValue(true);
        }
    }
}
