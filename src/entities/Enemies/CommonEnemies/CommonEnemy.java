package entities.Enemies.CommonEnemies;

import entities.Enemies.Enemy;
import mvc.views.enemyviews.EnemyObserver;

import java.util.List;

public abstract class CommonEnemy extends Enemy {
    protected int attack;

    public CommonEnemy(List<EnemyObserver> observers) {
        super(observers);
    }

    public CommonEnemy() {}

    @Override
    public int attackAmount() {
        return attack;
    }
}
