package mvc.observers;

import entities.ItemContainers.ItemContainer;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import mvc.views.CharacterView;
import playercharacter.PlayerCharacter;

public class CharacterObserver {
    protected final CharacterView characterView;

    public CharacterObserver(CharacterView characterView) {
        this.characterView = characterView;
    }

    public void unequipped(Equipable equipable) {
        characterView.showUnequippedMessage(equipable);
    }

    public void increasedHealth(PlayerCharacter character, int increasedAmount) {
        // leaving character parameter here for potential future compatibility with views
        // that may need the character's state
        characterView.showHealthIncreasedMessage(increasedAmount);
    }

    public void increasedMana(PlayerCharacter character, int increasedAmount) {
        // leaving character parameter here for potential future compatibility with views
        // that may need the character's state
        characterView.showManaIncreasedMessage(increasedAmount);
    }

    public void triedEscaping(PlayerCharacter character) {
        characterView.showFailedEscapeMessage(character);
    }

    public void equipped(Equipable equipable) {
        characterView.showEquippedMessage(equipable);
    }

    public void itemTaken(Item item) {
        characterView.showTakenItemMessage(item);
    }

    public void accessedEmptyItemContainer(ItemContainer container) {
        characterView.showEmptyItemContainerMessage(container);
    }

    public void looked(PlayerCharacter character, GameMap map) {
        characterView.showCurrentPlaceEntitiesInfo(character, map);
    }

    public void requestedLocationOnMap(PlayerCharacter character, GameMap map) {
        characterView.showLocationOnMap(character, map);
    }
}
