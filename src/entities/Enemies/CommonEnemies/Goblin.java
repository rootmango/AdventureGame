package entities.Enemies.CommonEnemies;

import mvc.views.MainView;

public class Goblin extends CommonEnemy {
    public Goblin(MainView mainView) {
        super(mainView);
        name = "Goblin";
        maxHealth = 50;
        currentHealth = maxHealth;
        attack = 8;
        xp = 15;
        deathMessage = "Goblin has died";
    }

    protected Goblin() {}
}
