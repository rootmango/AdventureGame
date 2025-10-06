package entities.ItemContainers;

import gamerandom.GameRNG;
import items.Equipables.IronDagger;
import items.Equipables.Staff;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public class Chest extends ItemContainer {
    public Chest() {
        name = "Chest";
        messageWhenEmpty = "Chest is empty!";
    }

    public Chest(List<ItemViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }

    @Override
    public void fill() {
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            list.add(new IronDagger(observers));
        } else if (random == 2) {
            list.add(new Staff(observers));
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
