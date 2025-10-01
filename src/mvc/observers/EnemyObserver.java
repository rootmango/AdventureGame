package mvc.observers;

import entities.Enemies.Enemy;
import mvc.views.EnemyView;
import playercharacter.PlayerCharacter;

public class EnemyObserver {
    protected final EnemyView enemyView;

    public EnemyObserver(EnemyView enemyView) {
        this.enemyView = enemyView;
    }

    public void died(Enemy enemy) {
        enemyView.showDeathMessage(enemy);
    }

    public void gotAttacked(Enemy enemy,int attackAmount) {
        enemyView.showGetAttackedMessage(enemy, attackAmount);
    }

    public void attacked(Enemy enemy, int attackAmount) {
        enemyView.showAttackMessage(enemy, attackAmount);
    }
}
