package entities.Enemies.CommonEnemies;

public class Undead extends CommonEnemy {
    public Undead() {
        name = "Undead";
        maxHealth = 65;
        currentHealth = maxHealth;
        attack = 10;
        xp = 25;
        deathMessage = "Undead has died... Hopefully for good this time!";
    }
}
