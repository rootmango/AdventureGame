package items.Equipables;

import gameexceptions.AnotherItemAlreadyEquippedException;
import items.Item;
import mvc.observers.ItemObserver;
import mvc.views.MainView;

public abstract class Equipable extends Item {
    protected int attackAmount;
    protected int manaConsumptionAmount;

    public Equipable(ItemObserver itemObserver) {
        super(itemObserver);
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
            itemObserver.failedAttemptEquipItem();
        }
    }
}
