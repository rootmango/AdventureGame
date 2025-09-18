package game;

import gameio.GameSerialization;
import maps.GameMap;
import mvc.Controller;
import mvc.View;
import playercharacter.*;
import quests.Quest;

import java.io.IOException;
import java.util.List;

public class Game {

    private static final View view = new View();
    private static final Controller controller = new Controller();

    public void gameLoop(PlayerCharacter character, GameMap map, List<Quest> questList,
                                MutableBoolean won, MutableBoolean dead, MutableBoolean quit,
                                long startTime, String saveName, CommandHandler commandHandler) {

        final Object lock = new Object();

        Thread autosaveThread = new Thread(() -> {
            boolean run = true;
            while (run) {
                try {
                    synchronized (lock) {
                        GameSerialization.createOrOverwriteSave(character, map, startTime, saveName);
                    }
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    run = false;
                } catch (IOException e) {
                    view.outputln("Error: " + e.getMessage());
                    run = false;
                }
            }
        });

        autosaveThread.start();

        while (!won.getValue() && !dead.getValue() && !quit.getValue()) {

            view.output("> ");

            String[] inputArgs = view.userInputString().split(" ");

            if (inputArgs.length > 0) {
                String command = controller.getCommandFromInputArgs(inputArgs);
                String[] args = controller.getCommandArgsFromInputArgs(inputArgs);

                if (command.equalsIgnoreCase("move")) {
                    synchronized (lock) {
                        commandHandler.move(character, map, args);
                    }
                } else if (command.equalsIgnoreCase("map")) {
                    commandHandler.map(character, map);
                } else if (command.equalsIgnoreCase("look")) {
                    commandHandler.look(character, map);
                } else if (command.equalsIgnoreCase("inventory")) {
                    commandHandler.inventory(character);
                } else if (command.equalsIgnoreCase("take")) {
                    synchronized (lock) {
                        commandHandler.take(character, map, args);
                    }
                } else if (command.equalsIgnoreCase("stats")) {
                    commandHandler.stats(character);
                } else if (command.equalsIgnoreCase("use")) {
                    synchronized (lock) {
                        commandHandler.use(character, args);
                    }
                } else if (command.equalsIgnoreCase("attack")) {
                    synchronized (lock) {
                        commandHandler.attack(character, map, args);
                    }
                } else if (command.equalsIgnoreCase("unequip")) {
                    synchronized (lock) {
                        commandHandler.unequip(character);
                    }
                } else if (command.equalsIgnoreCase("equipped")) {
                    commandHandler.equipped(character);
                } else if (command.equalsIgnoreCase("quests")) {
                    commandHandler.quests(questList);
                } else if (command.equalsIgnoreCase("save")) {
                    synchronized (lock) {
                        commandHandler.save(character, map, startTime, saveName);
                    }
                } else if (command.equalsIgnoreCase("quit")) {
                    synchronized (lock) {
                        commandHandler.quit(quit);
                    }
                } else {
                    commandHandler.printHelpCommands();
                }
            }

            synchronized (lock) {
                questList.forEach(Quest::setOrUpdateCompleted);
                if (character.isDead()) {
                    autosaveThread.interrupt();
                    dead.setValue(true);
                }
            }

        }

        autosaveThread.interrupt();
        onExit(character, map, won, dead, quit, startTime, saveName, commandHandler);

    }

    private void onExit(PlayerCharacter character, GameMap map, MutableBoolean won,
                               MutableBoolean dead, MutableBoolean quit, long startTime, String saveName,
                               CommandHandler commandHandler) {
        if (quit.getValue() == true) {
            view.outputln("Quitting game...");
            view.outputln("\nCharacter stats:");
            commandHandler.stats(character);
            view.outputln();
            commandHandler.save(character, map, startTime, saveName);
        } else if (won.getValue() == true) {
            view.outputln("You have won the game!");
            view.outputln("\nCharacter stats:");
            commandHandler.stats(character);
            view.outputln();
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                view.outputln("Couldn't delete save: ");
            }
        } else if (dead.getValue() == true) {
            view.outputln("You have died!");
            Time.printElapsedTime(startTime);
            try {
                GameSerialization.deleteSave(saveName);
            } catch (IOException e) {
                view.outputln("Couldn't delete save: ");
            }
        }
    }

}
