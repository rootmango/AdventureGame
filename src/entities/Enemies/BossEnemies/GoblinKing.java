package entities.Enemies.BossEnemies;

public class GoblinKing extends BossEnemy {
    public GoblinKing() {
        name = "Goblin King";
        maxHealth = 150;
        currentHealth = maxHealth;
        minAttack = 12;
        maxAttack = 18;
        xp = 100;
        deathMessage = "You have defeated the Goblin King!";
    }
}
