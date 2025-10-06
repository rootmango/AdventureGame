package items.Consumables;

import items.Item;
import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public abstract class Consumable extends Item {
    protected int currentHealthIncreaseAmount;
    protected int currentManaIncreaseAmount;

    public Consumable(List<ItemViewInterface> observers) {
        super(observers);
    }

    protected Consumable() {}

    @Override
    public void use() {
        owner.increaseHealth(currentHealthIncreaseAmount);
        owner.increaseMana(currentManaIncreaseAmount);
        owner.removeFromItemList(this);
    }
}
