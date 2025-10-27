package mvc.views.characterviews;

import entities.ItemContainers.ItemContainer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import playercharacter.PlayerCharacter;

public interface CharacterObserver {
    void showInventory(PlayerCharacter character);

    void showCharacterTypeNames();

    void showOnChosenCharacterType(String characterTypeName);

    boolean isValidCharacterTypeName(String characterTypeName);

    void onUnequipped(Equipable equipable);

    void onHealthIncreased(int increasedAmount);

    void onManaIncreased(int increasedAmount);

    void onFailedEscape(PlayerCharacter character);

    void onEquipped(Equipable equipable);

    void onItemTaken(Item item);

    void onEmptyItemContainer(ItemContainer container);

    void showCurrentPlaceEntitiesInfo(PlayerCharacter character, GameMap map);

    void showLocationOnMap(int xCoordinate, int yCoordinate, GameMap map);

    void showCharacterStats(PlayerCharacter character);

    void onNoSuchItemInInventory();

    void onNoSuchItemCurrentlyEquipped();

    void onAlreadyEquippedItem(PlayerCharacter character);
}
