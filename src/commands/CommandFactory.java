package commands;

import gameexceptions.EmptyCommandNameException;
import gameexceptions.InvalidCommandNameException;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class CommandFactory {
    protected Command commandFromName(String commandName, CommandParameters commandParams) {
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

    protected Command commandFromName(String commandName, CommandParameters commandParams,
                                      List<CommandEventListener> commandViews) {
        Command command = commandFromName(commandName, commandParams);
        command.addCommandEventListeners(commandViews);
        return command;
    }

    public Command createCommand(String commandName, CommandParameters commandParams)
            throws InvalidCommandNameException, EmptyCommandNameException {
        commandName = commandName.toLowerCase().strip();
        if (!commandName.isBlank()) {
            return commandFromName(commandName, commandParams);
        } else {
            throw new EmptyCommandNameException("Command name cannot be empty!");
        }
    }

    public Command createCommand(String commandName, CommandParameters commandParams,
                                 List<CommandEventListener> commandViews)
            throws InvalidCommandNameException, EmptyCommandNameException {
        commandName = commandName.toLowerCase().strip();
        if (!commandName.isBlank()) {
            return commandFromName(commandName, commandParams, commandViews);
        } else {
            throw new EmptyCommandNameException("Command name cannot be empty!");
        }
    }
}
