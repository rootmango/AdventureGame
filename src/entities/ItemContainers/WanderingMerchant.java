package entities.ItemContainers;

import gamerandom.GameRNG;
import items.Consumables.HealthPotion;
import items.Consumables.ManaPotion;

public class WanderingMerchant extends ItemContainer {
    public WanderingMerchant() {
        name = "Wandering Merchant";
        messageWhenEmpty = "Wandering Merchant has no more items!";
    }

    @Override
    public void fill() {
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            list.add(new HealthPotion());
        } else if (random == 2) {
            list.add(new ManaPotion());
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
