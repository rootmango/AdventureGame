package items.Consumables;

import mvc.views.itemviews.ItemObserver;

import java.util.List;

public class ManaPotion extends Consumable {
    public ManaPotion() {
        name = "Mana Potion";
        currentManaIncreaseAmount = 20;
    }

    public ManaPotion(List<ItemObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
