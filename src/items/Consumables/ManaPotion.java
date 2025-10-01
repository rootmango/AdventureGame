package items.Consumables;

import mvc.observers.ItemObserver;

public class ManaPotion extends Consumable {
    public ManaPotion(ItemObserver itemObserver) {
        super(itemObserver);
        name = "Mana Potion";
        currentManaIncreaseAmount = 20;
    }

    protected ManaPotion() {}
}
