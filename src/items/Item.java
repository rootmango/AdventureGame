package items;

import mvc.views.itemviews.ItemView;
import mvc.views.itemviews.ItemViewInterface;
import playercharacter.PlayerCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Serializable {
    protected String name;
    protected transient PlayerCharacter owner;
    protected final transient List<ItemViewInterface> observers = new ArrayList<>();

    public void addObservers(List<ItemViewInterface> observers) {
        this.observers.addAll(observers);
    }

    public Item(List<ItemViewInterface> observers) {
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
