package items.Equipables;

import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public class Staff extends Equipable {
    public Staff() {
        attackAmount = 15;
        manaConsumptionAmount = 10;
        name = "Staff";
    }

    public Staff(List<ItemViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
