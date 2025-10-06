package items.Consumables;

import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public class ManaPotion extends Consumable {
    public ManaPotion() {
        name = "Mana Potion";
        currentManaIncreaseAmount = 20;
    }

    public ManaPotion(List<ItemViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
