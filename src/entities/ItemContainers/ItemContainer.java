package entities.ItemContainers;

import entities.Entity;
import items.Item;
import mvc.views.itemviews.ItemObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ItemContainer extends Entity {
    protected final transient List<ItemObserver> observers = new ArrayList<>();

    public ItemContainer(List<ItemObserver> observers) {
        this.observers.addAll(observers);
        list.forEach(item -> item.addObservers(observers));
    }

    public ItemContainer() {}

    public void addObservers(List<ItemObserver> observers) {
        this.observers.addAll(observers);
        list.forEach(item -> item.addObservers(observers));
    }

    protected List<Item> list = new ArrayList<>();
    protected String messageWhenEmpty;

    public abstract void fill();

    public String getMessageWhenEmpty() {
        return messageWhenEmpty;
    }

    public Optional<Item> takeItem() {
        if (!list.isEmpty()) {
            return Optional.of(list.removeFirst());
        } else {
            return Optional.empty();
        }
    }
}
