package entities.Enemies;

import entities.Entity;
import mvc.observers.EnemyObserver;
import mvc.views.EnemyView;
import mvc.views.MainView;
import playercharacter.*;

public abstract class Enemy extends Entity {
    protected final EnemyObserver enemyObserver;

    protected int maxHealth;
    protected int currentHealth;
    protected int xp;
    protected boolean isDead = false;
    protected String deathMessage;

    public Enemy(EnemyObserver enemyObserver) {
        this.enemyObserver = enemyObserver;
    }

    public int getXP() {
        return xp;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    public Enemy() {
        // set only to avoid compiler error.
        // this value doesn't matter, gson will still change it upon deserialization.
        enemyObserver = new EnemyObserver(new EnemyView());
    }

    public abstract int attackAmount();

    /**
     * Checks if this {@code Enemy} is dead, and if so, sets its {@code isDead}
     * to {@code true}, and prints the {@code Enemy}'s {@code deathMessage}
     */
    protected void checkAndSetIfDead(PlayerCharacter character) {
        if (currentHealth <= 0) {
            isDead = true;
            enemyObserver.died(this);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    private void characterAttack(PlayerCharacter character) {
        int characterAttackAmount = character.attackAmount();
        currentHealth -= characterAttackAmount;
        enemyObserver.gotAttacked(this, characterAttackAmount);
    }

    private void characterReceiveAttack(PlayerCharacter character) {
        int attackAmount = this.attackAmount();
        character.subtractHealth(attackAmount);
        enemyObserver.attacked(this, attackAmount);
    }

    public void getAttacked(PlayerCharacter character) {
        characterAttack(character);
        this.checkAndSetIfDead(character);
        if (!isDead) {
            characterReceiveAttack(character);
        }
    }

}
