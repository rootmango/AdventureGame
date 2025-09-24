package entities.Enemies.CommonEnemies;

import entities.Enemies.Enemy;
import mvc.views.MainView;

public abstract class CommonEnemy extends Enemy {
    protected int attack;

    public CommonEnemy(MainView mainView) {
        super(mainView);
    }

    public CommonEnemy() {}

    @Override
    public int attackAmount() {
        return attack;
    }
}
