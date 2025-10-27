package items.Equipables;

import mvc.views.itemviews.ItemObserver;

import java.util.List;

public class Staff extends Equipable {
    public Staff() {
        attackAmount = 15;
        manaConsumptionAmount = 10;
        name = "Staff";
    }

    public Staff(List<ItemObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
