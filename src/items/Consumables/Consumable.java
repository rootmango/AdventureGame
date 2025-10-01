package items.Consumables;

import items.Item;
import mvc.observers.ItemObserver;

public abstract class Consumable extends Item {
    protected int currentHealthIncreaseAmount;
    protected int currentManaIncreaseAmount;

    public Consumable(ItemObserver itemObserver) {
        super(itemObserver);
    }

    protected Consumable() {}

    @Override
    public void use() {
        owner.increaseHealth(currentHealthIncreaseAmount);
        owner.increaseMana(currentManaIncreaseAmount);
        owner.removeFromItemList(this);
    }
}
