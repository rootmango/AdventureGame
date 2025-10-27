package entities.Enemies.BossEnemies;

import entities.Enemies.Enemy;
import gamerandom.GameRNG;
import mvc.views.enemyviews.EnemyObserver;
import playercharacter.PlayerCharacter;

import java.util.List;

public abstract class BossEnemy extends Enemy {
    protected int minAttack;
    protected int maxAttack;

    public BossEnemy(List<EnemyObserver> observers) {
        super(observers);
    }

    public BossEnemy() {}

    @Override
    public int attackAmount() {
        return GameRNG.randomInRange(minAttack, maxAttack);
    }

    @Override
    protected void checkAndSetIfDead(PlayerCharacter character) {
        if (currentHealth <= 0) {
            isDead = true;
            character.afterDefeatingBossEnemy();
            observers.forEach(observer -> observer.onDied(this));
        }
    }
}
