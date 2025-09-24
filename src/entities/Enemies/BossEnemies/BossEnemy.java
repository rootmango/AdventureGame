package entities.Enemies.BossEnemies;

import entities.Enemies.Enemy;
import gamerandom.GameRNG;
import mvc.views.MainView;
import playercharacter.PlayerCharacter;

public abstract class BossEnemy extends Enemy {
    protected int minAttack;
    protected int maxAttack;

    public BossEnemy(MainView mainView) {
        super(mainView);
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
            character.addXP(xp);
            mainView.outputln(deathMessage);
        }
    }
}
