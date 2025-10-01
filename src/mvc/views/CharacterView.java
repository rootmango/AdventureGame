package mvc.views;

import entities.ItemContainers.ItemContainer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import playercharacter.PlayerCharacter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterView extends MainView {
    protected final GameMapView mapView;

    public CharacterView(GameMapView mapView) {
        this.mapView = mapView;
    }

    public void showInventory(PlayerCharacter character) {
        List<Item> itemList = character.getItems();
        if (itemList.isEmpty()) {
            outputln("inventory empty");
        } else {
            outputln("Inventory");
            itemList.stream().collect(Collectors.groupingBy(Item::getName, Collectors.counting()))
                    .forEach((k, v) -> outputln("- " + v + "x " + k));
        }
    }

    public void showCharacterTypeNames() {
        PlayerCharacter.characterTypes.values().forEach(value -> {
            outputln("- " + (value.description()));
        });
    }

    public void showOnChosenCharacterType(String characterTypeName) {
        if (PlayerCharacter.characterTypes.containsKey(characterTypeName.toLowerCase())) {
            outputln(PlayerCharacter.characterTypes.get(characterTypeName).onChosen());
        }
    }

    public boolean isValidCharacterTypeName(String characterTypeName) {
        Set<String> validCharacterTypeNames = PlayerCharacter.characterTypes.keySet();
        return validCharacterTypeNames.contains(characterTypeName.toLowerCase().strip());
    }

    public void showUnequippedMessage(Equipable equipable) {
        outputln(("Unequipped " + equipable.getName()));
    }

    public void showHealthIncreasedMessage(int increasedAmount) {
        outputln("Health increased by " + increasedAmount);
    }

    public void showManaIncreasedMessage(int increasedAmount) {
        outputln("Mana increased by " + increasedAmount);
    }

    public void showFailedEscapeMessage(PlayerCharacter character) {
        String currentBossEnemyName = character.getCurrentBossEnemyName();
        outputln("Can't escape from this battle! Defeat " + currentBossEnemyName + " first!");
    }

    public void showEquippedMessage(Equipable equipable) {
        outputln(equipable.getName() + " equipped");
    }

    public void showTakenItemMessage(Item item) {
        outputln(item.getName() + " taken");
    }

    public void showEmptyItemContainerMessage(ItemContainer container) {
        outputln(container.getMessageWhenEmpty());
    }

    public void showCurrentPlaceEntitiesInfo(PlayerCharacter character, GameMap map) {
        int xCoordinate = character.getXCoordinate();
        int yCoordinate = character.getYCoordinate();
        outputln(mapView.entitiesInfoAt(map, xCoordinate, yCoordinate));
    }

    public void showLocationOnMap(PlayerCharacter character, GameMap map) {
        int xCoordinate = character.getXCoordinate();
        int yCoordinate = character.getYCoordinate();
        outputln("You are at (" + xCoordinate + ", " + yCoordinate + ") - " +
                map.at(xCoordinate, yCoordinate).getName());
    }

    public void showCharacterStats(PlayerCharacter character) {
        outputln(character.stats());
    }

    public void showNoSuchItemInInventoryMessage() {
        outputln("No such item in inventory");
    }

    public void showNoSuchItemCurrentlyEquippedMessage() {
        outputln("No item is currently equipped!");
    }

    public void showCharacterEquippedItem(PlayerCharacter character) {
        if (!character.hasEquippedItem()) {
            outputln("no equipped item");
        } else {
            Equipable equippedItem = character.getEquippedItemList().getFirst();
            outputln(equippedItem.getName());
        }
    }
}
