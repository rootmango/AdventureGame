package gamerandom;

import entities.Enemies.CommonEnemies.Goblin;
import entities.Enemies.CommonEnemies.Ogre;
import entities.Enemies.CommonEnemies.Thief;
import entities.Enemies.CommonEnemies.Undead;
import entities.Enemies.Enemy;
import mvc.views.enemyviews.EnemyViewInterface;

import java.util.ArrayList;
import java.util.List;

public class RandomCommonEnemyGenerator implements RandomEntityGenerator {
    protected final List<EnemyViewInterface> observers = new ArrayList<>();

    public RandomCommonEnemyGenerator(List<EnemyViewInterface> observers) {
        this.observers.addAll(observers);
    }

    @Override
    public Enemy generate() {
        int random = GameRNG.randomInRange(1, 11);
        // Probabilities:
        // Goblin - 5 in 11
        // Thief - 3 in 11
        // Ogre - 2 in 11
        // Undead - 1 in 11
        if (random <= 5) {
            return new Goblin(observers);
        } else if (random >= 6 && random <= 8) {
            return new Thief(observers);
        } else if (random >= 9 && random <= 10) {
            return new Undead(observers);
        } else if (random == 11) {
            return new Ogre(observers);
        } else {
            throw new RuntimeException("Unexpected error");
        }
    }
}
