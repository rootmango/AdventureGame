package entities.Enemies.BossEnemies;

import mvc.views.enemyviews.EnemyViewInterface;

import java.util.List;

public class GoblinKing extends BossEnemy {
    public GoblinKing() {
        name = "Goblin King";
        maxHealth = 150;
        currentHealth = maxHealth;
        minAttack = 12;
        maxAttack = 18;
        xp = 100;
        deathMessage = "You have defeated the Goblin King!";
    }

    public GoblinKing(List<EnemyViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
