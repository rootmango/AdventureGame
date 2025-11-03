package commands;

import gameexceptions.EmptyCommandNameException;
import gameexceptions.InvalidCommandNameException;
import mvc.views.commandviews.CommandEventListener;

import java.util.List;

public class CommandFactory {
    protected Command commandFromName(String commandName, CommandParameters commandParams,
                                      CommandEventListener commandEventListener) {
        return switch (commandName) {
            case "attack" -> new AttackCommand(commandParams, commandEventListener);
            case "equipped" -> new EquippedCommand(commandParams, commandEventListener);
            case "help" -> new HelpCommand(commandParams, commandEventListener);
            case "inventory" -> new InventoryCommand(commandParams, commandEventListener);
            case "look" -> new LookCommand(commandParams, commandEventListener);
            case "map" -> new MapCommand(commandParams, commandEventListener);
            case "move" -> new MoveCommand(commandParams, commandEventListener);
            case "quests" -> new QuestsCommand(commandParams, commandEventListener);
            case "save" -> new SaveCommand(commandParams, commandEventListener);
            case "stats" -> new StatsCommand(commandParams, commandEventListener);
            case "take" -> new TakeCommand(commandParams, commandEventListener);
            case "unequip" -> new UnequipCommand(commandParams, commandEventListener);
            case "use" -> new UseCommand(commandParams, commandEventListener);
            case "quit" -> new QuitCommand(commandParams, commandEventListener);
            default -> throw new InvalidCommandNameException("Invalid command name!");
        };
    }

    public Command createCommand(String commandName, CommandParameters commandParams,
                                 CommandEventListener commandEventListener)
            throws InvalidCommandNameException, EmptyCommandNameException {
        commandName = commandName.toLowerCase().strip();
        if (!commandName.isBlank()) {
            return commandFromName(commandName, commandParams, commandEventListener);
        } else {
            throw new EmptyCommandNameException("Command name cannot be empty!");
        }
    }
}
