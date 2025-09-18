package entities.Enemies.BossEnemies;

import entities.Enemies.Enemy;
import gamerandom.GameRNG;
import playercharacter.PlayerCharacter;

public abstract class BossEnemy extends Enemy {
    protected int minAttack;
    protected int maxAttack;

    @Override
    public int attackAmount() {
        return GameRNG.randomInRange(minAttack, maxAttack);
    }

    @Override
    protected void checkAndSetIfDead(PlayerCharacter character) {
        if (currentHealth <= 0) {
            isDead = true;
            character.afterDefeatingBossEnemy();
            character.addXP(xp);
            view.outputln(deathMessage);
        }
    }
}
