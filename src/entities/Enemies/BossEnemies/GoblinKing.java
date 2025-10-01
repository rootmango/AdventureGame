package entities.Enemies.BossEnemies;

import mvc.observers.EnemyObserver;
import mvc.views.MainView;

public class GoblinKing extends BossEnemy {
    public GoblinKing(EnemyObserver enemyObserver) {
        super(enemyObserver);
        name = "Goblin King";
        maxHealth = 150;
        currentHealth = maxHealth;
        minAttack = 12;
        maxAttack = 18;
        xp = 100;
        deathMessage = "You have defeated the Goblin King!";
    }

    protected GoblinKing() {}
}
