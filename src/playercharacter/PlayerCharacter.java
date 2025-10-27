package playercharacter;

import entities.Enemies.Enemy;
import entities.ItemContainers.ItemContainer;
import gameexceptions.*;
import gamerandom.GameRNG;
import items.Deserializers.ItemDeserializer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import maps.Place;
import mvc.views.characterviews.CharacterObserver;
import mvc.views.itemviews.ItemObserver;
import mvc.views.placeviews.PlaceObserver;

import java.io.Serializable;
import java.util.*;

public class PlayerCharacter implements Serializable {
    private final transient List<CharacterObserver> characterObservers = new ArrayList<>();
    private final transient List<PlaceObserver> placeObservers = new ArrayList<>();

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
    private int xp = 190;

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

    public void addCharacterObservers(List<CharacterObserver> characterObservers) {
        this.characterObservers.addAll(characterObservers);
    }

    public void addPlaceObservers(List<PlaceObserver> observers) {
        this.placeObservers.addAll(observers);
    }

    public void addItemObservers(List<ItemObserver> observers) {
        itemList.forEach(item -> item.addObservers(observers));
        equippedItem.forEach(item -> item.addObservers(observers));
    }


    public void increaseHealth(int amount) {
        if (currentHealth + amount >= maxHealth) {
            int increasedAmount = maxHealth - currentHealth;
            currentHealth = maxHealth;
            characterObservers.forEach(observer -> observer.onHealthIncreased(increasedAmount));
        } else {
            currentHealth += amount;
            characterObservers.forEach(observer -> observer.onHealthIncreased(amount));
        }
    }

    public void increaseMana(int amount) {
        if (currentMana + amount >= maxMana) {
            int increasedAmount = maxMana - currentMana;
            currentMana = maxMana;
            characterObservers.forEach(observer -> observer.onManaIncreased(increasedAmount));
        } else {
            currentMana += amount;
            characterObservers.forEach(observer -> observer.onManaIncreased(amount));
        }
    }

    public static Map<String, CharacterStats> characterTypes = new LinkedHashMap<>(Map.ofEntries(
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

    public PlayerCharacter(String characterTypeName) {
        if (characterTypes.containsKey(characterTypeName.toLowerCase())) {
            CharacterStats stats = characterTypes.get(characterTypeName.toLowerCase());
            maxHealth = stats.maxHealth();
            currentHealth = maxHealth;
            maxMana = stats.maxMana();
            currentMana = maxMana;
            minAttack = stats.minAttack();
            maxAttack = stats.maxAttack();
        } else {
            throw new RuntimeException();
        }
    }

    public PlayerCharacter(String characterTypeName,
                           List<CharacterObserver> characterObservers) {
        this(characterTypeName);
        this.characterObservers.addAll(characterObservers);
    }

    public PlayerCharacter(String characterTypeName,
                           List<CharacterObserver> characterObservers,
                           List<PlaceObserver> placeObservers) {
        this(characterTypeName, characterObservers);
        this.placeObservers.addAll(placeObservers);
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    protected PlayerCharacter() {
//        characterObservers = new ArrayList<>();
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
        characterObservers.forEach(observer -> observer.showCurrentPlaceEntitiesInfo(this, map));
    }



    public void move(GameMap map, Direction direction) {
        if (isFightingBossEnemy()) {
            characterObservers.forEach(observer -> observer.onFailedEscape(this));
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
                throw new InaccessiblePlaceException(nextPlace);
            } else {
                if (nextPlace.isFortress()) {
                    if (nextPlace.containsBossEnemy()) {
                        setFightingBossEnemy(nextPlace.getBossEnemyNameIfAny());
                    }
                }
                this.xCoordinate = nextPlaceX;
                this.yCoordinate = nextPlaceY;
                placeObservers.forEach(observer -> observer.showEntryMessage(nextPlace));
            }
        }
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
            characterObservers.forEach(observer -> observer.onEquipped(equipable));
        }
    }

    public void unequip() {
        if (equippedItem.isEmpty()) {
            throw new NoEquippedItemException();
        } else {
            Equipable equipable = equippedItem.removeFirst();
            characterObservers.forEach(observer -> observer.onUnequipped(equipable));
            itemList.add(equipable);
        }
    }

    public void takeFromItemContainer(ItemContainer container) {
        container.takeItem()
                .ifPresentOrElse(
                item -> {
                    item.setOwner(this);
                    itemList.add(item);
                    characterObservers.forEach(observer -> observer.onItemTaken(item));
                },
                () -> characterObservers.forEach(observer -> observer.onEmptyItemContainer(container))
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
        int equippedItemManaConsumptionAmount;

        if (hasEquippedItem()) {
            Equipable item = equippedItem.getFirst();
            equippedItemAttackAmount = item.getAttackAmount();
            equippedItemManaConsumptionAmount = item.getManaConsumptionAmount();

            if (consumeMana(equippedItemManaConsumptionAmount)) {
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

    public void attack(Enemy enemy) {
        int attackAmount = this.attackAmount();
        enemy.receiveDamage(this, attackAmount);
    }
}
