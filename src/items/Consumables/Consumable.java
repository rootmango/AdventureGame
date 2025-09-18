package items.Consumables;

import items.Item;

public abstract class Consumable extends Item {
    protected int currentHealthIncreaseAmount;
    protected int currentManaIncreaseAmount;

    @Override
    public void use() {
        owner.increaseHealth(currentHealthIncreaseAmount);
        owner.increaseMana(currentManaIncreaseAmount);
        owner.removeFromItemList(this);
    }
}
