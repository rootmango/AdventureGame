package entities.Enemies.CommonEnemies;

import entities.Enemies.Enemy;

public abstract class CommonEnemy extends Enemy {
    protected int attack;

    @Override
    public int attackAmount() {
        return attack;
    }
}
