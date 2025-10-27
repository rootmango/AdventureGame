package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class LookCommand extends Command {
    public LookCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public LookCommand(CommandParameters commandParams, List<CommandEventListener> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        character.look(map);
    }
}
