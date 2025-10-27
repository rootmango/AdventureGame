package items;

import mvc.views.itemviews.ItemObserver;
import playercharacter.PlayerCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Serializable {
    protected String name;
    protected transient PlayerCharacter owner;
    protected final transient List<ItemObserver> observers = new ArrayList<>();

    public void addObservers(List<ItemObserver> observers) {
        this.observers.addAll(observers);
    }

    public Item(List<ItemObserver> observers) {
        this.observers.addAll(observers);
    }

    public Item() {}

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
