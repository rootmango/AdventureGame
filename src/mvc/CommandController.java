package mvc;

import entities.Enemies.Enemy;
import game.MutableBoolean;
import gameexceptions.NoEquippedItemException;
import gameio.GameSerialization;
import maps.GameMap;
import maps.Place;
import playercharacter.PlayerCharacter;
import playercharacter.Direction;
import quests.Quest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class CommandController {
    private static final View view = new View();
    private static final MainController controller = new MainController();

    private static final Set<String> MOVE_UP_VALUES = Set.of(
            "north"
//            "up",
//            "w"
    );
    private static final Set<String> MOVE_LEFT_VALUES = Set.of(
            "west"
//            "left",
//            "a"
    );
    private static final Set<String> MOVE_RIGHT_VALUES = Set.of(
            "east"
//            "right",
//            "d"
    );
    private static final Set<String> MOVE_DOWN_VALUES = Set.of(
            "south"
//            "down",
//            "s"
    );

    public boolean checkUp(String s) {
        return MOVE_UP_VALUES.contains(s.toLowerCase());
    }

    public boolean checkLeft(String s) {
        return MOVE_LEFT_VALUES.contains(s.toLowerCase());
    }

    public boolean checkRight(String s) {
        return MOVE_RIGHT_VALUES.contains(s.toLowerCase());
    }

    public boolean checkDown(String s) {
        return MOVE_DOWN_VALUES.contains(s.toLowerCase());
    }

    public String getSubjectNameFromCommandArgs(String[] commandArgs) {
        return String.join(" ", Arrays.copyOfRange(commandArgs, 0, commandArgs.length));
        // joins together the rest of the line in case of multi-word names
        // so that, if the command is "attack goblin king", the method will return "goblin king"
    }

    public void move(PlayerCharacter character, GameMap map, String... args) {
        if (args.length == 0) {
            printHelpCommands();
        } else {
            if (checkUp(args[0])) {
                view.outputln("Moving north");
                character.move(map, Direction.UP);
            } else if (checkLeft(args[0])) {
                view.outputln("Moving west");;
                character.move(map, Direction.LEFT);
            } else if (checkRight(args[0])) {
                view.outputln("Moving east");;
                character.move(map, Direction.RIGHT);
            } else if (checkDown(args[0])) {
                view.outputln("Moving south");;
                character.move(map, Direction.DOWN);
            }
        }
    }

    public void map(PlayerCharacter character, GameMap map) {
        character.locationOnMap(map);
    }

    public void look(PlayerCharacter character, GameMap map) {
        character.look(map);
    }

    public void inventory(PlayerCharacter character) {
        character.printInventory();
    }

    public void take(PlayerCharacter character, GameMap map, String... args) {
        if (args.length == 0) {
            printHelpCommands();
        } else {
            String itemContainerName = getSubjectNameFromCommandArgs(args);
            Place currentPlace = character.getCurrentPlace(map);
            try {
                character.takeItemByName(currentPlace, itemContainerName);
            } catch (NoSuchElementException e) {
                view.outputln("No such item container here");
            }
        }
    }

    public void stats(PlayerCharacter character) {
        view.outputln(character.stats());
    }

    public void use(PlayerCharacter character, String... args) {
        if (args.length == 0) {
            printHelpCommands();
        } else {
            String itemName = getSubjectNameFromCommandArgs(args);
            try {
                character.useItemByName(itemName);
            } catch (NoSuchElementException e) {
                view.outputln("No such item in inventory");
            }
        }
    }

    public void attack(PlayerCharacter character, GameMap map, String... args) {
        if (args.length == 0) {
            printHelpCommands();
        } else {
            String enemyName = getSubjectNameFromCommandArgs(args);
            Place currentPlace = character.getCurrentPlace(map);
            try {
                Enemy enemy = currentPlace.getEnemies().stream()
                        .filter(x -> x.getName().equalsIgnoreCase(enemyName))
                        .findFirst()
                        .orElseThrow(NoSuchElementException::new);
                enemy.getAttacked(character);
                if (enemy.isDead()) {
                    currentPlace.getEnemies().remove(enemy);
                }
            } catch (NoSuchElementException e) {
                view.outputln("No such enemy here");
            }
        }
    }

    public void unequip(PlayerCharacter character) {
        try {
            character.unequip();
        } catch (NoEquippedItemException e) {
            view.outputln("No item is currently equipped!");
        }
    }

    public void equipped(PlayerCharacter character) {
        character.printEquippedItem();
    }

    public void quests(List<Quest> questList) {
        questList.forEach(q -> view.outputln(q.infoStatus()));
    }

    public void printHelpCommands() {
        view.outputln();
        view.outputln("Available commands you can use:\n");

        view.outputln("move [north | east | west | south] - moves to the desired direction");
        view.outputln("map - shows your current coordinates");
        view.outputln("look - shows what's around you");
        view.outputln("inventory - shows all the items you currently have in your inventory");
        view.outputln("take [item container] - takes an item from an item container "
                                                        + "(for example, \"take chest\")");
        view.outputln("stats - shows you character's stats");
        view.outputln("use [item] - uses an item from your inventory. If it's a consumable "
                + "(for example, a potion), consumes it, and if it's an equipable "
                + "(for  example, weapons), equips it.");
        view.outputln("attack [enemy] - attacks a nearby enemy (for example, \"attack goblin\")");
        view.outputln("unequip - if you have equipped an item, unequips it and "
                                                        + "puts it back in your inventory");
        view.outputln("equipped - shows your equipped item, if you have one");
        view.outputln("quests - shows the quests you have and your progress in them");
        view.outputln("save - saves your current progress. Note that your progress is periodically "
                                                        + "autosaved as well");
        view.outputln("help - shows this list of commands if you forget them");

        view.outputln();
    }

    public void save(PlayerCharacter character, GameMap map, long startTime, String saveName) {
        try {
            GameSerialization.createOrOverwriteSave(character, map, startTime, saveName);
            view.outputln("Saved to \"" + saveName + "\"");
        } catch (IOException e) {
            view.outputln("Error saving: " + e.getMessage());
        }
    }

    public void quit(MutableBoolean quit) {
        quit.setValue(true);
    }
}
