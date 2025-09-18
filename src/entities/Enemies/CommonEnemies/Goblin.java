package entities.Enemies.CommonEnemies;

public class Goblin extends CommonEnemy {
    public Goblin() {
        name = "Goblin";
        maxHealth = 50;
        currentHealth = maxHealth;
        attack = 8;
        xp = 15;
        deathMessage = "Goblin has died";
    }
}
