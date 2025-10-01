package items.Consumables;

import mvc.observers.ItemObserver;

public class HealthPotion extends Consumable {
    public HealthPotion(ItemObserver itemObserver) {
        super(itemObserver);
        name = "Health Potion";
        currentHealthIncreaseAmount = 20;
    }

    protected HealthPotion() {}
}
