package commands;

import gameexceptions.InvalidCommandNameException;

public class CommandFactory {
    public Command validateCommandName(String commandName, CommandParameters commandParams) {
        commandName = commandName.toLowerCase();
        return switch (commandName) {
            case "attack" -> new AttackCommand(commandParams);
            case "equipped" -> new EquippedCommand(commandParams);
            case "help" -> new HelpCommand(commandParams);
            case "inventory" -> new InventoryCommand(commandParams);
            case "look" -> new LookCommand(commandParams);
            case "map" -> new MapCommand(commandParams);
            case "move" -> new MoveCommand(commandParams);
            case "quests" -> new QuestsCommand(commandParams);
            case "save" -> new SaveCommand(commandParams);
            case "stats" -> new StatsCommand(commandParams);
            case "take" -> new TakeCommand(commandParams);
            case "unequip" -> new UnequipCommand(commandParams);
            case "use" -> new UseCommand(commandParams);
            case "quit" -> new QuitCommand(commandParams);
            default -> throw new InvalidCommandNameException("Invalid command name!");
        };
    }

    public Command createCommand(String commandName, CommandParameters commandParams)
            throws InvalidCommandNameException {
        commandName = commandName.strip();
        if (!commandName.isBlank()) {
            return validateCommandName(commandName, commandParams);
        } else {
            throw new InvalidCommandNameException("Command name cannot be empty!");
        }
    }
}
