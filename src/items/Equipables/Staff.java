package items.Equipables;

import mvc.observers.ItemObserver;

public class Staff extends Equipable {
    public Staff(ItemObserver itemObserver) {
        super(itemObserver);
        attackAmount = 15;
        manaConsumptionAmount = 10;
        name = "Staff";
    }

    protected Staff() {}
}
