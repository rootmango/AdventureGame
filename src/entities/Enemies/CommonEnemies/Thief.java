package entities.Enemies.CommonEnemies;

import mvc.views.MainView;

public class Thief extends CommonEnemy {
    public Thief(MainView mainView) {
        super(mainView);
        name = "Thief";
        maxHealth = 40;
        currentHealth = maxHealth;
        attack = 7;
        xp = 12;
        deathMessage = "Thief has died";
    }

    protected Thief() {}
}
