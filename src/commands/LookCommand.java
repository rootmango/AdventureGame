package commands;

import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class LookCommand extends Command {
    public LookCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    @Override
    public void execute() {
        character.look(map);
    }
}
