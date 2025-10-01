package commands;

public class EquippedCommand extends Command{
    public EquippedCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        characterView.showCharacterEquippedItem(character);
    }
}
