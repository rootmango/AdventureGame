package entities.Enemies;

import entities.Entity;
import mvc.views.enemyviews.EnemyView;
import mvc.views.enemyviews.EnemyViewInterface;
import playercharacter.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {
    protected final transient List<EnemyViewInterface> observers = new ArrayList<>();

    protected int maxHealth;
    protected int currentHealth;
    protected int xp;
    protected boolean isDead = false;
    protected String deathMessage;

    public Enemy(List<EnemyViewInterface> observers) {
        this.observers.addAll(observers);
    }

    public Enemy() {}

    public void addObservers(List<EnemyViewInterface> observers) {
        this.observers.addAll(observers);
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

    public abstract int attackAmount();

    /**
     * Checks if this {@code Enemy} is dead, and if so, sets its {@code isDead}
     * to {@code true}, and prints the {@code Enemy}'s {@code deathMessage}
     */
    protected void checkAndSetIfDead(PlayerCharacter character) {
        if (currentHealth <= 0) {
            isDead = true;
            observers.forEach(observer -> observer.onDied(this));
        }
    }

    public boolean isDead() {
        return isDead;
    }

    private void characterAttack(PlayerCharacter character) {
        int characterAttackAmount = character.attackAmount();
        currentHealth -= characterAttackAmount;
        observers.forEach(observer -> observer.onGotAttacked(this, characterAttackAmount));
    }

    private void characterReceiveAttack(PlayerCharacter character) {
        int attackAmount = this.attackAmount();
        character.subtractHealth(attackAmount);
        observers.forEach(observer -> observer.onAttacked(this, attackAmount));
    }

    public void getAttacked(PlayerCharacter character) {
        characterAttack(character);
        this.checkAndSetIfDead(character);
        if (!isDead) {
            characterReceiveAttack(character);
        }
    }

}
