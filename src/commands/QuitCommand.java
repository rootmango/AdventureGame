package commands;

public class QuitCommand extends Command {
    public QuitCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            quit.setValue(true);
        }
    }
}
