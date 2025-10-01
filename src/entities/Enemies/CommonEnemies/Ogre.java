package entities.Enemies.CommonEnemies;

import mvc.observers.EnemyObserver;
import mvc.views.MainView;

public class Ogre extends CommonEnemy {
    public Ogre(EnemyObserver enemyObserver) {
        super(enemyObserver);
        name = "Ogre";
        maxHealth = 100;
        currentHealth = maxHealth;
        attack = 9;
        xp = 30;
        deathMessage = "Ogre has died";
    }

    protected Ogre() {}
}
