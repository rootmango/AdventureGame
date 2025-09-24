package entities.Enemies.BossEnemies;

import mvc.views.MainView;

public class GoblinKing extends BossEnemy {
    public GoblinKing(MainView mainView) {
        super(mainView);
        name = "Goblin King";
        maxHealth = 150;
        currentHealth = maxHealth;
        minAttack = 12;
        maxAttack = 18;
        xp = 100;
        deathMessage = "You have defeated the Goblin King!";
    }

    protected GoblinKing() {}
}
