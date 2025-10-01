package entities.ItemContainers;

import gamerandom.GameRNG;
import items.Equipables.IronDagger;
import items.Equipables.Staff;
import mvc.observers.ItemObserver;

public class Chest extends ItemContainer {
    public Chest(ItemObserver itemObserver) {
        super(itemObserver);
        name = "Chest";
        messageWhenEmpty = "Chest is empty!";
    }

    protected Chest() {}

    @Override
    public void fill() {
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            list.add(new IronDagger(itemObserver));
        } else if (random == 2) {
            list.add(new Staff(itemObserver));
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
