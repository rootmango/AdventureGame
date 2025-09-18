package entities.Enemies.CommonEnemies;

public class Thief extends CommonEnemy {
    public Thief() {
        name = "Thief";
        maxHealth = 40;
        currentHealth = maxHealth;
        attack = 7;
        xp = 12;
        deathMessage = "Thief has died";
    }
}
