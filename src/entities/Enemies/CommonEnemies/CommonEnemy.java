package entities.Enemies.CommonEnemies;

import entities.Enemies.Enemy;
import mvc.observers.EnemyObserver;
import mvc.views.MainView;

public abstract class CommonEnemy extends Enemy {
    protected int attack;

    public CommonEnemy(EnemyObserver enemyObserver) {
        super(enemyObserver);
    }

    public CommonEnemy() {}

    @Override
    public int attackAmount() {
        return attack;
    }
}
