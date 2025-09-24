package entities.Enemies.CommonEnemies;

import mvc.views.MainView;

public class Undead extends CommonEnemy {
    public Undead(MainView mainView) {
        super(mainView);
        name = "Undead";
        maxHealth = 65;
        currentHealth = maxHealth;
        attack = 10;
        xp = 25;
        deathMessage = "Undead has died... Hopefully for good this time!";
    }

    protected Undead() {}
}
