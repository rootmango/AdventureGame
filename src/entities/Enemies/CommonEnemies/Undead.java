package entities.Enemies.CommonEnemies;

import mvc.views.enemyviews.EnemyObserver;

import java.util.List;

public class Undead extends CommonEnemy {
    public Undead() {
        name = "Undead";
        maxHealth = 65;
        currentHealth = maxHealth;
        attack = 10;
        xp = 25;
        deathMessage = "Undead has died... Hopefully for good this time!";
    }

    public Undead(List<EnemyObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
