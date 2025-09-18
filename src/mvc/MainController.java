package mvc;

import game.GameLoopCoreParams;
import game.MutableBoolean;
import maps.GameMap;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainController {
    public String getCommandFromInputArgs(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return "";
        } else {
            return inputArgs[0];
        }
    }

    public String[] getCommandArgsFromInputArgs(String[] inputArgs) {
        return Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
    }

    public void handleCommandInput(View view, CommandController commandController, Object lock,
                                   GameLoopCoreParams gameLoopCoreParams,
                                   long startTime, MutableBoolean quit) {

        PlayerCharacter character = gameLoopCoreParams.character();
        GameMap map = gameLoopCoreParams.map();
        List<Quest> questList = gameLoopCoreParams.questList();
        String saveName = gameLoopCoreParams.saveName();

        view.output("> ");

        String[] inputArgs = view.userInputString().split(" ");

        if (inputArgs.length > 0) {
            String command = getCommandFromInputArgs(inputArgs);
            String[] args = getCommandArgsFromInputArgs(inputArgs);

            if (command.equalsIgnoreCase("move")) {
                synchronized (lock) {
                    commandController.move(character, map, args);
                }
            } else if (command.equalsIgnoreCase("map")) {
                commandController.map(character, map);
            } else if (command.equalsIgnoreCase("look")) {
                commandController.look(character, map);
            } else if (command.equalsIgnoreCase("inventory")) {
                commandController.inventory(character);
            } else if (command.equalsIgnoreCase("take")) {
                synchronized (lock) {
                    commandController.take(character, map, args);
                }
            } else if (command.equalsIgnoreCase("stats")) {
                commandController.stats(character);
            } else if (command.equalsIgnoreCase("use")) {
                synchronized (lock) {
                    commandController.use(character, args);
                }
            } else if (command.equalsIgnoreCase("attack")) {
                synchronized (lock) {
                    commandController.attack(character, map, args);
                }
            } else if (command.equalsIgnoreCase("unequip")) {
                synchronized (lock) {
                    commandController.unequip(character);
                }
            } else if (command.equalsIgnoreCase("equipped")) {
                commandController.equipped(character);
            } else if (command.equalsIgnoreCase("quests")) {
                commandController.quests(questList);
            } else if (command.equalsIgnoreCase("save")) {
                synchronized (lock) {
                    commandController.save(character, map, startTime, saveName);
                }
            } else if (command.equalsIgnoreCase("quit")) {
                synchronized (lock) {
                    commandController.quit(quit);
                }
            } else {
                commandController.printHelpCommands();
            }
        }

    }
}
