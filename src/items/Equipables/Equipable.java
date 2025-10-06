package items.Equipables;

import gameexceptions.AnotherItemAlreadyEquippedException;
import items.Item;
import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public abstract class Equipable extends Item {
    protected int attackAmount;
    protected int manaConsumptionAmount;

    public Equipable(List<ItemViewInterface> observers) {
        super(observers);
    }

    protected Equipable() {}

    public int getAttackAmount() {
        return attackAmount;
    }

    public int getManaConsumptionAmount() {
        return manaConsumptionAmount;
    }

    @Override
    public void use() {
        try {
            owner.setEquippedItem(this);
            owner.removeFromItemList(this);
        } catch (AnotherItemAlreadyEquippedException e) {
            observers.forEach(ItemViewInterface::onAnotherItemAlreadyEquipped);
        }
    }
}
