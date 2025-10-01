package entities.Enemies.CommonEnemies;

import mvc.observers.EnemyObserver;
import mvc.views.MainView;

public class Goblin extends CommonEnemy {
    public Goblin(EnemyObserver enemyObserver) {
        super(enemyObserver);
        name = "Goblin";
        maxHealth = 50;
        currentHealth = maxHealth;
        attack = 8;
        xp = 15;
        deathMessage = "Goblin has died";
    }

    protected Goblin() {}
}
