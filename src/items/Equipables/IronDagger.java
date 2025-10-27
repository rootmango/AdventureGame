package items.Equipables;

import mvc.views.itemviews.ItemObserver;

import java.util.List;

public class IronDagger extends Equipable {
    public IronDagger() {
        attackAmount = 7;
        manaConsumptionAmount = 0;
        name = "Iron Dagger";
    }

    public IronDagger(List<ItemObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
