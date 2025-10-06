package mvc.views.enemyviews;

import entities.Enemies.Enemy;
import mvc.views.MainView;

public class EnemyView extends MainView implements EnemyViewInterface {
    public void onDied(Enemy enemy) {
        outputln(enemy.getDeathMessage());
    }

    public void onGotAttacked(Enemy enemy, int attackAmount) {
        String enemyName = enemy.getName();
        outputln("You attack " + enemyName + " for " + attackAmount + " damage");
    }

    public void onAttacked(Enemy enemy, int attackAmount) {
        String enemyName = enemy.getName();
        int currentHealth = enemy.getCurrentHealth();
        outputln(enemyName + " attacks back for " + attackAmount + " damage");
        outputln(enemyName + " has " + currentHealth + " remaining health");
    }
}
