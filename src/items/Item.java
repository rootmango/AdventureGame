package items;

import mvc.observers.ItemObserver;
import mvc.views.ItemView;
import playercharacter.PlayerCharacter;

import java.io.Serializable;

public abstract class Item implements Serializable {
    protected String name;
    protected transient PlayerCharacter owner;
    protected final ItemObserver itemObserver;

    public Item(ItemObserver itemObserver) {
        this.itemObserver = itemObserver;
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    public Item() {
        // set only to avoid compiler error.
        // this value doesn't matter, gson will still change it upon deserialization.
        itemObserver = new ItemObserver(new ItemView());
    }

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
