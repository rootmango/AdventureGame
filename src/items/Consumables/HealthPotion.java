package items.Consumables;

import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;

import java.util.List;

public class HealthPotion extends Consumable {
    public HealthPotion() {
        name = "Health Potion";
        currentHealthIncreaseAmount = 20;
    }

    public HealthPotion(List<ItemViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
