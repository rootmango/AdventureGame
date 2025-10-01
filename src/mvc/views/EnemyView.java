package mvc.views;

import entities.Enemies.Enemy;
import playercharacter.PlayerCharacter;

public class EnemyView extends MainView {
    public void showDeathMessage(Enemy enemy) {
        outputln(enemy.getDeathMessage());
    }

    public void showGetAttackedMessage(Enemy enemy, int attackAmount) {
        String enemyName = enemy.getName();
        outputln("You attack " + enemyName + " for " + attackAmount + " damage");
    }

    public void showAttackMessage(Enemy enemy, int attackAmount) {
        String enemyName = enemy.getName();
        int currentHealth = enemy.getCurrentHealth();
        outputln(enemyName + " attacks back for " + attackAmount + " damage");
        outputln(enemyName + " has " + currentHealth + " remaining health");
    }
}
