package entities.Enemies.CommonEnemies;

public class Ogre extends CommonEnemy {
    public Ogre() {
        name = "Ogre";
        maxHealth = 100;
        currentHealth = maxHealth;
        attack = 9;
        xp = 30;
        deathMessage = "Ogre has died";
    }
}
