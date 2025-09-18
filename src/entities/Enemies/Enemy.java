package entities.Enemies;

import entities.Entity;
import mvc.View;
import playercharacter.*;

public abstract class Enemy extends Entity {
    protected static final View view = new View();

    protected int maxHealth;
    protected int currentHealth;
    protected int xp;
    protected boolean isDead = false;
    protected String deathMessage;

    public abstract int attackAmount();

    /**
     * Checks if this {@code Enemy} is dead, and if so, sets its {@code isDead}
     * to {@code true}, adds XP to the {@code PlayerCharacter} the {@code Enemy}
     * is fighting, and prints the {@code Enemy}'s {@code deathMessage}
     */
    protected void checkAndSetIfDead(PlayerCharacter character) {
        if (currentHealth <= 0) {
            isDead = true;
            character.addXP(xp);
            view.outputln(deathMessage);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void getAttacked(PlayerCharacter character) {
        int characterAttackAmount = character.attackAmount();
        int attackAmount = this.attackAmount();
        currentHealth -= characterAttackAmount;
        character.consumeMana();
        view.outputln("You attack " + getName() + " for " + characterAttackAmount + " damage");
        this.checkAndSetIfDead(character);
        if (!isDead) {
            character.subtractHealth(attackAmount);
            view.outputln(getName() + " attacks back for " + attackAmount + " damage");
            view.outputln(getName() + " has " + currentHealth + " remaining health");
        }
    }

}
