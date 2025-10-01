package commands;

public class LookCommand extends Command {
    public LookCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        character.look(map);
    }
}
