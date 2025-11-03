package maps;

import entities.*;
import entities.Enemies.BossEnemies.BossEnemy;
import entities.Enemies.BossEnemies.GoblinKing;
import entities.Enemies.Enemy;
import entities.ItemContainers.ItemContainer;
import gameexceptions.UnrecognizedCharException;
import gamerandom.GameRNG;
import mvc.controllers.game.*;
import gamerandom.RandomCommonEnemyGenerator;
import gamerandom.RandomItemContainerGenerator;
import mvc.views.enemyviews.EnemyObserver;

import java.io.Serializable;
import java.util.*;

public class Place implements Serializable {
    private final int xCoordinate;
    private final int yCoordinate;
    private final char symbol;
    private final String name;
    private final String description;
    private boolean isBorder = false;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<ItemContainer> itemContainers = new ArrayList<>();
    private boolean isLocked = false;
    private transient String inaccessibleMessage = "";
    private boolean isFortress = false;

    public static final HashMap<Character, NameAndDescription> PLACES = new HashMap<>(Map.ofEntries(
            Map.entry('D', new NameAndDescription("Dark Forest", "Entering a dark forest...")),
            Map.entry('I', new NameAndDescription("Misty Glade", "Entering a misty glade...")),
            Map.entry('W', new NameAndDescription("Winding Riverbank",
                    "Arriving at a winding riverbank...")),
            Map.entry('C', new NameAndDescription("Crumbling Cliff",
                    "Arriving at a crumbling cliff...")),
            Map.entry('P', new NameAndDescription("Whispering Pines",
                    "Stepping into whispering pines...")),
            Map.entry('S', new NameAndDescription("Sandy Dunes",
                    "Stepping into sandy dunes...")),
            Map.entry('L', new NameAndDescription("Gloomy Woods",
                    "Stepping into gloomy woods...")),
            Map.entry('Y', new NameAndDescription("Dusty Trail",
                    "Stepping into a dusty trail...")),
            Map.entry('H', new NameAndDescription("Echoing Valley",
                    "Entering an echoing valley...")),
            Map.entry('R', new NameAndDescription("Frosted Meadow",
                    "Entering a frosted meadow...")),
            Map.entry('K', new NameAndDescription("Rocky Basin",
                    "Approaching a rocky basin...")),
            Map.entry('N', new NameAndDescription("Sunny Canyon",
                    "Stepping into a sunny canyon...")),
            Map.entry('V', new NameAndDescription("Silent Cove",
                    "Approaching a silent cove...")),
            Map.entry('Z', new NameAndDescription("Frozen Lake Shore",
                    "Approaching a frozen lake shore...")),
            Map.entry('B', new NameAndDescription("Breezy Seafront",
                    "Approaching a breezy seafront...")),
            Map.entry('g', new NameAndDescription("Glistening Sands",
                    "Stepping into glistening sands...")),
            Map.entry('c', new NameAndDescription("Coral Bay", "Approaching a coral bay...")),
            Map.entry('p', new NameAndDescription("Palm-Fringed Beach",
                    "Stepping into a palm-fringed breach..."))
            )
    );

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public char getChar() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<ItemContainer> getItemContainers() {
        return itemContainers;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public Place(int xCoordinate, int yCoordinate, char c)
            throws UnrecognizedCharException {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        symbol = c;
        if (PLACES.containsKey(c)) {
            name = PLACES.get(c).name();
            description = PLACES.get(c).description();
        } else if (c == 'E') {
            name = "Entry";
            description = "entry placeholder";
        } else if (c == 'F') {
            name = "Fortress";
            description = "fortress placeholder";
            isFortress = true;
            isLocked = true;
            inaccessibleMessage = "Fortress is currently locked! You can't enter it right now!";
        } else if (c == '0') {
            name = "Border";
            description = "border placeholder";
            isBorder = true;
            inaccessibleMessage = "You've reached the borders of the kingdom! You can't move further!";
        } else {
            throw new UnrecognizedCharException();
        }
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    protected Place() {
        // set only to avoid compiler error.
        // none of these values matter, gson will still change them upon deserialization.
        xCoordinate = 0;
        yCoordinate = 0;
        symbol = '0';
        name = null;
        description = null;
    }

    /**
     * Fills the current place with a random number of distinct common enemies.
     */
    public void seedWithCommonEnemies(RandomCommonEnemyGenerator randomCommonEnemyGenerator) {
        // For simplicity of the mvc.controllers.game interface's sake, each place may not contain more than one type
        // of enemy or item container.
        //
        // Since this is a text mvc.controllers.game, the player typing "attack goblin" when there are 3 goblins will only
        // result in confusion, since the player will not know which of the 3 is currently attacked.
        //
        // A possible solution is attaching an id to each entity, however typing an id (even if it's a
        // single-digit id for the number-in-row) every time could quickly become annoying to the player.
        //
        // Nevertheless, I have chosen to keep "enemies" and "itemContainers" as lists and not sets for
        // flexibility should any of the above solutions be implemented.

        int enemiesCount = GameRNG.randomInRange(1, 3);
        int enemiesIterator = 0;
        do {
            Enemy enemy = randomCommonEnemyGenerator.generate();
            if (!enemies.contains(enemy)) {
                enemies.add(enemy);
                enemiesIterator++;
            }
        } while (enemiesIterator < enemiesCount);
    }

    /**
     * Fills the current place with a random number of distinct item containers.
     */
    public void seedWithItemContainers(RandomItemContainerGenerator randomItemContainerGenerator) {
        // For simplicity of the mvc.controllers.game interface's sake, each place may not contain more than one type
        // of enemy or item container.
        //
        // Since this is a text mvc.controllers.game, the player typing "attack goblin" when there are 3 goblins will only
        // result in confusion, since the player will not know which of the 3 is currently attacked.
        //
        // A possible solution is attaching an id to each entity, however typing an id (even if it's a
        // single-digit id for the number-in-row) every time could quickly become annoying to the player.
        //
        // Nevertheless, I have chosen to keep "enemies" and "itemContainers" as lists and not sets for
        // flexibility should any of the above solutions be implemented.

        int itemContainersCount = GameRNG.randomInRange(1, 1);
        int containersIterator = 0;
        do {
            ItemContainer itemContainer = randomItemContainerGenerator.generate();
            itemContainer.fill();
            if (!itemContainers.contains(itemContainer)) {
                itemContainers.add(itemContainer);
                containersIterator++;
            }
        } while (containersIterator < itemContainersCount);
    }

    public void seedWithGoblinKing(List<EnemyObserver> enemyObservers) {
        enemies.add(new GoblinKing(enemyObservers));
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void unlock() {
        isLocked = false;
    }

    public String getInaccessibleMessage() {
        return inaccessibleMessage;
    }

    public boolean isFortress() {
        return isFortress;
    }

    public boolean containsBossEnemy() {
        return enemies.stream()
                .anyMatch(enemy -> enemy instanceof BossEnemy);
    }

    public String getBossEnemyNameIfAny() {
        return enemies.stream()
                .filter(enemy -> enemy instanceof BossEnemy)
                .map(Entity::getName)
                .findFirst()
                .orElse("");
    }

    public String getEntryMessage() {
        if (PLACES.containsKey(symbol)) {
            return PLACES.get(symbol).description();
        } else if (name.equalsIgnoreCase("Entry")) {
            return "You have returned to the place from which you entered this kingdom!";
        } else if (name.equalsIgnoreCase("Fortress")) {
            return "Entering the Fortress of the Goblin King!";
        } else {
            throw new UnrecognizedCharException();
        }
    }
}
