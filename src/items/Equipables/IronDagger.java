package items.Equipables;

import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public class IronDagger extends Equipable {
    public IronDagger() {
        attackAmount = 7;
        manaConsumptionAmount = 0;
        name = "Iron Dagger";
    }

    public IronDagger(List<ItemViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
