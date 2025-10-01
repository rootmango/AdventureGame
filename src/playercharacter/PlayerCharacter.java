package playercharacter;

import entities.ItemContainers.ItemContainer;
import gameexceptions.*;
import gamerandom.GameRNG;
import items.Deserializers.ItemDeserializer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import maps.Place;
import mvc.observers.CharacterObserver;
import mvc.observers.PlaceObserver;
import mvc.views.CharacterView;
import mvc.views.GameMapView;
import mvc.views.MainView;
import mvc.views.PlaceView;

import java.io.Serializable;
import java.util.*;

public class PlayerCharacter implements Serializable {
    private final MainView mainView;
    private final GameMapView mapView;
    private final CharacterObserver characterObserver;
    private final PlaceObserver placeObserver;

    private int maxHealth;
    private int maxMana;
    private int currentHealth;
    private int currentMana;
    private int minAttack;
    private int maxAttack;
    private int xCoordinate;
    private int yCoordinate;
    private final List<Item> itemList = new ArrayList<>();
    private final List<Equipable> equippedItem = new ArrayList<>();
    private int xp = 0;

    private boolean isFightingBossEnemy = false;
    private String currentBossEnemyName = "";

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getMinAttack() {
        return minAttack;
    }

    public int getMaxAttack() {
        return maxAttack;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public List<Item> getItems() {
        return itemList;
    }

    public List<Equipable> getEquippedItemList() {
        return equippedItem;
    }

    public String getCurrentBossEnemyName() {
        return currentBossEnemyName;
    }


    public void increaseHealth(int amount) {
        if (currentHealth + amount >= maxHealth) {
            int increasedAmount = maxHealth - currentHealth;
            currentHealth = maxHealth;
            characterObserver.increasedHealth(this, increasedAmount);
        } else {
            currentHealth += amount;
            characterObserver.increasedHealth(this, amount);
        }
    }

    public void increaseMana(int amount) {
        if (currentMana + amount >= maxMana) {
            int increasedAmount = maxMana - currentMana;
            currentMana = maxMana;
            characterObserver.increasedMana(this, increasedAmount);
        } else {
            currentMana += amount;
            characterObserver.increasedMana(this, amount);
        }
    }

    public static Map<String, CharacterStats> characterTypes = new HashMap<>(Map.ofEntries(
            Map.entry("warrior",
                    new CharacterStats(100, 80, 10, 20,
                            "Warrior - More health", "You have chosen warrior!")),
            Map.entry("mage",
                    new CharacterStats(80, 120, 10, 20,
                            "Mage - More mana (used for magic attacks)",
                            "You have chosen mage!")),
            Map.entry("rogue",
                    new CharacterStats(60, 90, 15, 25,
                            "Rogue - More attack", "You have chosen rogue!"))
    ));

    public PlayerCharacter(String characterTypeName, MainView mainView, GameMapView mapView,
                           CharacterObserver characterObserver, PlaceObserver placeObserver) {
        if (characterTypes.containsKey(characterTypeName.toLowerCase())) {
            CharacterStats stats = characterTypes.get(characterTypeName.toLowerCase());
            maxHealth = stats.maxHealth();
            currentHealth = maxHealth;
            maxMana = stats.maxMana();
            currentMana = maxMana;
            minAttack = stats.minAttack();
            maxAttack = stats.maxAttack();
            this.mainView = mainView;
            this.mapView = mapView;
            this.characterObserver = characterObserver;
            this.placeObserver = placeObserver;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    protected PlayerCharacter() {
        mainView = new MainView();
        mapView = new GameMapView();
        characterObserver = new CharacterObserver(new CharacterView(mapView));
        placeObserver = new PlaceObserver(new PlaceView());
    }



    public void findCharacterAndSetCoordinates(GameMap map) throws CharacterNotFoundException {
        Place[][] fullMap = map.getFullMap();
        boolean found = false;
        for (int i = 0; i < fullMap.length; i++) {
            for (int j = 0; j < fullMap[i].length; j++) {
                if (fullMap[i][j].getChar() == 'E') {
                    xCoordinate = j;
                    yCoordinate = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        if (found == false) {
            throw new CharacterNotFoundException("Entry point not found");
        }
    }



    public void look(GameMap map) {
        characterObserver.looked(this, map);
    }



    public void move(GameMap map, Direction direction) {
        if (isFightingBossEnemy()) {
            characterObserver.triedEscaping(this);
        } else {
            int nextPlaceX = -1;
            int nextPlaceY = -1;
            if (direction == Direction.UP) {
                nextPlaceX = this.xCoordinate;
                nextPlaceY = this.yCoordinate - 1;
            } else if (direction == Direction.LEFT) {
                nextPlaceX = this.xCoordinate - 1;
                nextPlaceY = this.yCoordinate;
            } else if (direction == Direction.RIGHT) {
                nextPlaceX = this.xCoordinate + 1;
                nextPlaceY = this.yCoordinate;
            } else if (direction == Direction.DOWN) {
                nextPlaceX = this.xCoordinate;
                nextPlaceY = this.yCoordinate + 1;
            }

            Place nextPlace = map.at(nextPlaceX, nextPlaceY);

            if (nextPlace.isBorder() || nextPlace.isLocked()) {
                placeObserver.attemptedEntryInaccessiblePlace(nextPlace);
            } else {
                if (nextPlace.isFortress()) {
                    if (nextPlace.containsBossEnemy()) {
                        setFightingBossEnemy(nextPlace.getBossEnemyNameIfAny());
                    }
                }
                this.xCoordinate = nextPlaceX;
                this.yCoordinate = nextPlaceY;
                placeObserver.enteredPlace(nextPlace);
            }
        }
    }

    public void locationOnMap(GameMap map) {
        characterObserver.requestedLocationOnMap(this, map);
    }

    public void removeFromItemList(Item item) {
        itemList.remove(item);
    }

    public void takeItemByName(Place place, String name) {
        place.getItemContainers().stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        this::takeFromItemContainer,
                        () -> { throw new NoSuchElementException(); }
                );
    }

    public void useItemByName(String name) {
        itemList.stream()
                .filter(x -> x.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        Item::use,
                        () -> { throw new NoSuchElementException(); }
                );
    }

    public boolean hasEquippedItem() {
        return !equippedItem.isEmpty();
    }

    public void setEquippedItem(Equipable equipable) {
        if (!equippedItem.isEmpty()) {
            throw new AnotherItemAlreadyEquippedException();
        } else {
            equippedItem.add(equipable);
            characterObserver.equipped(equipable);
        }
    }

    public void unequip() {
        if (equippedItem.isEmpty()) {
            throw new NoEquippedItemException();
        } else {
            Equipable equipable = equippedItem.removeFirst();
            characterObserver.unequipped(equipable);
            itemList.add(equipable);
        }
    }

    public void takeFromItemContainer(ItemContainer container) {
        container.takeItem()
                .ifPresentOrElse(
                item -> {
                    item.setOwner(this);
                    itemList.add(item);
                    characterObserver.itemTaken(item);
                },
                () -> characterObserver.accessedEmptyItemContainer(container)
                );
    }

    public Place getCurrentPlace(GameMap map) {
        return map.at(xCoordinate, yCoordinate);
    }

    public String stats() {
        String equippedStatsIfAny = "";
        if (hasEquippedItem()) {
            var item = equippedItem.getFirst();
            equippedStatsIfAny = "\n" + item.getName() + " Bonus Attack Points: " + item.getAttackAmount()
            + "\n" + item.getName() + " Mana Consumption Amount: " + item.getManaConsumptionAmount();
        }

        return "Max Health: " + maxHealth + "\n" +
                "Current Health: " + currentHealth + "\n" +
                "Max Mana: " + maxMana + "\n" +
                "Current Mana: " + currentMana + "\n" +
                "Attack Points: " + minAttack + " - " + maxAttack +
                equippedStatsIfAny + "\n" +
                "XP: " + xp;
    }

    public void subtractHealth(int amount) {
        currentHealth -= amount;
    }

    public void subtractMana(int amount) {
        currentMana -= amount;
    }

    public int attackAmount() {
        int baseAttackAmount = GameRNG.randomInRange(minAttack, maxAttack);
        int equippedItemAttackAmount;

        if (hasEquippedItem()) {
            Equipable item = equippedItem.getFirst();
            equippedItemAttackAmount = item.getAttackAmount();

            if (consumeMana(equippedItemAttackAmount)) {
                return baseAttackAmount + equippedItemAttackAmount;
            } else {
                return baseAttackAmount;
            }
        } else {
            return baseAttackAmount;
        }
    }

    public boolean hasEnoughMana(int amount) {
        return amount <= currentMana;
    }

    public boolean consumeMana(int amount) {
        if (hasEnoughMana(amount)) {
            currentMana -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getXP() {
        return xp;
    }

    public void addXP(int amount) {
        xp += amount;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }


    public boolean isFightingBossEnemy() {
        return isFightingBossEnemy;
    }

    public void setFightingBossEnemy(String bossEnemyName) {
        isFightingBossEnemy = true;
        currentBossEnemyName = bossEnemyName;
    }

    public void afterDefeatingBossEnemy() {
        isFightingBossEnemy = false;
        currentBossEnemyName = "";
    }


    /**
     * Sets {@code owner} of every {@code Item} in {@code equippedItem} and {@code itemList}, if any.
     * Should be used after deserializing, since all {@code PlayerCharacter}'s {@code Item}'s
     * {@code owner}s will be set to null after deserialization to avoid circular referencing.
     * See {@link ItemDeserializer}'s documentation for more information.
     */
    public void setOwnerForAllItems() {
        for (var item: equippedItem) {
            item.setOwner(this);
        }
        for (var item: itemList) {
            item.setOwner(this);
        }
    }
}
