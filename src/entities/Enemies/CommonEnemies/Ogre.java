package entities.Enemies.CommonEnemies;

import mvc.views.enemyviews.EnemyObserver;

import java.util.List;

public class Ogre extends CommonEnemy {
    public Ogre() {
        name = "Ogre";
        maxHealth = 100;
        currentHealth = maxHealth;
        attack = 9;
        xp = 30;
        deathMessage = "Ogre has died";
    }

    public Ogre(List<EnemyObserver> observers) {
        this();
        this.observers.addAll(observers);
    }
}
