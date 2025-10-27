package items.Consumables;

import mvc.views.itemviews.ItemObserver;

import java.util.List;

public class HealthPotion extends Consumable {
    public HealthPotion() {
        name = "Health Potion";
        currentHealthIncreaseAmount = 20;
    }

    public HealthPotion(List<ItemObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
