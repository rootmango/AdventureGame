package items.Equipables;

import mvc.observers.ItemObserver;

public class IronDagger extends Equipable {
    public IronDagger(ItemObserver itemObserver) {
        super(itemObserver);
        attackAmount = 7;
        manaConsumptionAmount = 0;
        name = "Iron Dagger";
    }

    protected IronDagger() {}
}
