package items.Equipables;

import gameexceptions.AnotherItemAlreadyEquippedException;
import items.Item;
import mvc.View;

public abstract class Equipable extends Item {
    protected static final View view = new View();

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
            view.outputln("An item is already equipped! Unequip it first to equip this one!");
        }
    }
}
