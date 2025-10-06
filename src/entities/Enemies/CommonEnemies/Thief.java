package entities.Enemies.CommonEnemies;

import mvc.views.enemyviews.EnemyViewInterface;

import java.util.List;

public class Thief extends CommonEnemy {
    public Thief() {
        name = "Thief";
        maxHealth = 40;
        currentHealth = maxHealth;
        attack = 7;
        xp = 12;
        deathMessage = "Thief has died";
    }

    public Thief(List<EnemyViewInterface> observers) {
        this();
        this.observers.addAll(observers);
    }
}
