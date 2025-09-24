package items.Equipables;

import gameexceptions.AnotherItemAlreadyEquippedException;
import items.Item;
import mvc.views.MainView;

public abstract class Equipable extends Item {
    protected final MainView mainView = new MainView();

    protected int attackAmount;
    protected int manaConsumptionAmount;

    public int attackAmountIfAny() {
        if (owner.hasEnoughMana(manaConsumptionAmount)) {
            return attackAmount;
        } else {
            return 0;
        }
    }

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
            mainView.outputln("An item is already equipped! Unequip it first to equip this one!");
        }
    }
}
