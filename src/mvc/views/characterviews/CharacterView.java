package mvc.views.characterviews;

import entities.ItemContainers.ItemContainer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import mvc.views.GameMapView;
import mvc.views.MainView;
import playercharacter.PlayerCharacter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterView extends MainView implements CharacterObserver {
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
        output("> ");
    }

    public void showOnChosenCharacterType(String characterTypeName) {
        if (PlayerCharacter.characterTypes.containsKey(characterTypeName.toLowerCase())) {
            outputln(PlayerCharacter.characterTypes.get(characterTypeName.toLowerCase()).onChosen());
        }
    }

    public boolean isValidCharacterTypeName(String characterTypeName) {
        Set<String> validCharacterTypeNames = PlayerCharacter.characterTypes.keySet();
        return validCharacterTypeNames.contains(characterTypeName.toLowerCase().strip());
    }

    public void onUnequipped(Equipable equipable) {
        outputln(("Unequipped " + equipable.getName()));
    }

    public void onHealthIncreased(int increasedAmount) {
        outputln("Health increased by " + increasedAmount);
    }

    public void onManaIncreased(int increasedAmount) {
        outputln("Mana increased by " + increasedAmount);
    }

    public void onFailedEscape(PlayerCharacter character) {
        String currentBossEnemyName = character.getCurrentBossEnemyName();
        outputln("Can't escape from this battle! Defeat " + currentBossEnemyName + " first!");
    }

    public void onEquipped(Equipable equipable) {
        outputln(equipable.getName() + " equipped");
    }

    public void onItemTaken(Item item) {
        outputln(item.getName() + " taken");
    }

    public void onEmptyItemContainer(ItemContainer container) {
        outputln(container.getMessageWhenEmpty());
    }

    public void showCurrentPlaceEntitiesInfo(PlayerCharacter character, GameMap map) {
        int xCoordinate = character.getXCoordinate();
        int yCoordinate = character.getYCoordinate();
        outputln(mapView.entitiesInfoAt(map, xCoordinate, yCoordinate));
    }

    public void showLocationOnMap(int xCoordinate, int yCoordinate, GameMap map) {
        outputln("You are at (" + xCoordinate + ", " + yCoordinate + ") - " +
                map.at(xCoordinate, yCoordinate).getName());
    }

    public void showCharacterStats(PlayerCharacter character) {
        outputln(character.stats());
    }

    public void onNoSuchItemInInventory() {
        outputln("No such item in inventory");
    }

    public void onNoSuchItemCurrentlyEquipped() {
        outputln("No item is currently equipped!");
    }

    public void onAlreadyEquippedItem(PlayerCharacter character) {
        if (!character.hasEquippedItem()) {
            outputln("no equipped item");
        } else {
            Equipable equippedItem = character.getEquippedItemList().getFirst();
            outputln(equippedItem.getName());
        }
    }
}
