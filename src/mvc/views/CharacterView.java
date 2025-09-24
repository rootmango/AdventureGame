package mvc.views;

import items.Item;
import playercharacter.PlayerCharacter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterView extends MainView {
    public void showInventory(PlayerCharacter character) {
        List<Item> itemList = character.getItems();
        if (itemList.isEmpty()) {
            System.out.println("inventory empty");
        } else {
            System.out.println("Inventory");
            itemList.stream().collect(Collectors.groupingBy(Item::getName, Collectors.counting()))
                    .forEach((k, v) -> System.out.println("- " + v + "x " + k));
        }
    }

    public void showCharacterTypeNames() {
        PlayerCharacter.characterTypes.values().forEach(value -> {
            System.out.println("- " + (value.description()));
        });
    }

    public void showOnChosenCharacterType(String characterTypeName) {
        if (PlayerCharacter.characterTypes.containsKey(characterTypeName.toLowerCase())) {
            System.out.println(PlayerCharacter.characterTypes.get(characterTypeName).onChosen());
        }
    }

    public boolean isValidCharacterTypeName(String characterTypeName) {
        Set<String> validCharacterTypeNames = PlayerCharacter.characterTypes.keySet();
        return validCharacterTypeNames.contains(characterTypeName.toLowerCase().strip());
    }
}
