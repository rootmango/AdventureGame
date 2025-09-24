package entities.Enemies.CommonEnemies;

import mvc.views.MainView;

public class Ogre extends CommonEnemy {
    public Ogre(MainView mainView) {
        super(mainView);
        name = "Ogre";
        maxHealth = 100;
        currentHealth = maxHealth;
        attack = 9;
        xp = 30;
        deathMessage = "Ogre has died";
    }

    protected Ogre() {}
}
