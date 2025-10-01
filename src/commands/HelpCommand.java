package commands;

public class HelpCommand extends Command{
    public HelpCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        commandObserver.requestedHelp();
    }
}
