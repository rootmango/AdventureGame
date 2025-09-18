package gamerandom;

import entities.Enemies.CommonEnemies.Goblin;
import entities.Enemies.CommonEnemies.Ogre;
import entities.Enemies.CommonEnemies.Thief;
import entities.Enemies.CommonEnemies.Undead;
import entities.Enemies.Enemy;
import entities.ItemContainers.Chest;
import entities.ItemContainers.ItemContainer;
import entities.ItemContainers.WanderingMerchant;

public class GameRandom {
    public static Enemy randomEnemy() {
        int random = GameRNG.randomInRange(1, 11);
        // Probabilities:
        // Goblin - 5 in 11
        // Thief - 3 in 11
        // Ogre - 2 in 11
        // Undead - 1 in 11
        if (random <= 5) {
            return new Goblin();
        } else if (random >= 6 && random <= 8) {
            return new Thief();
        } else if (random >= 9 && random <= 10) {
            return new Undead();
        } else if (random == 11) {
            return new Ogre();
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }

    public static ItemContainer randomItemContainer() {
        ItemContainer itemContainer;
        int random = GameRNG.randomInRange(1, 2);
        if (random == 1) {
            itemContainer = new Chest();
        } else if (random == 2) {
            itemContainer = new WanderingMerchant();
        } else {
            throw new RuntimeException("Unexpected error");
        }
        itemContainer.fill();
        return itemContainer;
    }
}
