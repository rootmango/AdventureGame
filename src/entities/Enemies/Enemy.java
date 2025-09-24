package entities.Enemies;

import entities.Entity;
import mvc.views.MainView;
import playercharacter.*;

public abstract class Enemy extends Entity {
    protected final MainView mainView;

    protected int maxHealth;
    protected int currentHealth;
    protected int xp;
    protected boolean isDead = false;
    protected String deathMessage;

    public Enemy(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    public Enemy() {
        // set only to avoid compiler error.
        // this value doesn't matter, gson will still change it upon deserialization.
        mainView = new MainView();
    }

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
            mainView.outputln(deathMessage);
        }
    }

    public boolean isDead() {
        return isDead;
    }

    private void characterAttack(PlayerCharacter character) {
        int characterAttackAmount = character.attackAmount();
        currentHealth -= characterAttackAmount;
        character.consumeMana();
        mainView.outputln("You attack " + getName() + " for " + characterAttackAmount + " damage");
    }

    private void characterReceiveAttack(PlayerCharacter character) {
        int attackAmount = this.attackAmount();
        character.subtractHealth(attackAmount);
        mainView.outputln(getName() + " attacks back for " + attackAmount + " damage");
        mainView.outputln(getName() + " has " + currentHealth + " remaining health");
    }

    public void getAttacked(PlayerCharacter character) {
        characterAttack(character);
        this.checkAndSetIfDead(character);
        if (!isDead) {
            characterReceiveAttack(character);
        }
    }

}
