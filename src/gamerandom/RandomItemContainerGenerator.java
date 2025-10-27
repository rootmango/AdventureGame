package gamerandom;

import entities.ItemContainers.Chest;
import entities.ItemContainers.ItemContainer;
import entities.ItemContainers.WanderingMerchant;
import mvc.views.itemviews.ItemObserver;

import java.util.ArrayList;
import java.util.List;

public class RandomItemContainerGenerator implements RandomEntityGenerator {
    protected final List<ItemObserver> observers = new ArrayList<>();

    public RandomItemContainerGenerator(List<ItemObserver> observers) {
        this.observers.addAll(observers);
    }

    @Override
    public ItemContainer generate() {
        ItemContainer itemContainer;
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            itemContainer = new Chest(observers);
        } else if (random == 2) {
            itemContainer = new WanderingMerchant(observers);
        } else {
            throw new RuntimeException("Unexpected error");
        }
        return itemContainer;
    }
}
