package entities.Enemies.CommonEnemies;

import entities.Enemies.Enemy;
import mvc.views.enemyviews.EnemyViewInterface;

import java.util.List;

public abstract class CommonEnemy extends Enemy {
    protected int attack;

    public CommonEnemy(List<EnemyViewInterface> observers) {
        super(observers);
    }

    public CommonEnemy() {}

    @Override
    public int attackAmount() {
        return attack;
    }
}
