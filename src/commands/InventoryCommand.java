package commands;

public class InventoryCommand extends Command {
    public InventoryCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    @Override
    public void execute() {
        characterView.showInventory(character);
    }
}
