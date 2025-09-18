package items;

import playercharacter.PlayerCharacter;

import java.io.Serializable;

public abstract class Item implements Serializable {
    protected String name;
    protected transient PlayerCharacter owner;

    public String getName() {
        return name;
    }

    public void setOwner(PlayerCharacter owner) {
        this.owner = owner;
    }

    public boolean hasOwner() {
        return !(owner == null);
    }

    public abstract void use();

}
