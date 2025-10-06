package commands;

import mvc.views.commandviews.CommandView;
import mvc.views.commandviews.CommandViewInterface;

import java.util.List;

public class LookCommand extends Command {
    public LookCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public LookCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    @Override
    public void execute() {
        character.look(map);
    }
}
