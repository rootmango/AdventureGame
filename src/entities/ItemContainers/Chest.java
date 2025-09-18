package entities.ItemContainers;

import gamerandom.GameRNG;
import items.Equipables.IronDagger;
import items.Equipables.Staff;

public class Chest extends ItemContainer {
    public Chest() {
        name = "Chest";
        messageWhenEmpty = "Chest is empty!";
    }

    @Override
    public void fill() {
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            list.add(new IronDagger());
        } else if (random == 2) {
            list.add(new Staff());
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
