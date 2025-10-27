package entities.ItemContainers;

import gamerandom.GameRNG;
import items.Consumables.HealthPotion;
import items.Consumables.ManaPotion;
import mvc.views.itemviews.ItemObserver;

import java.util.List;

public class WanderingMerchant extends ItemContainer {
    public WanderingMerchant() {
        name = "Wandering Merchant";
        messageWhenEmpty = "Wandering Merchant has no more items!";
    }

    public WanderingMerchant(List<ItemObserver> observers) {
        this();
        this.observers.addAll(observers);
    }

    @Override
    public void fill() {
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            list.add(new HealthPotion(observers));
        } else if (random == 2) {
            list.add(new ManaPotion(observers));
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
