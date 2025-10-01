package commands;

public class StatsCommand extends Command {
    public StatsCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        characterView.showCharacterStats(character);
    }
}
