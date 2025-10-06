package entities.ItemContainers;

import entities.Entity;
import items.Item;
import mvc.views.itemviews.ItemViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ItemContainer extends Entity {
    protected final transient List<ItemViewInterface> observers = new ArrayList<>();

    public ItemContainer(List<ItemViewInterface> observers) {
        this.observers.addAll(observers);
        list.forEach(item -> item.addObservers(observers));
    }

    public ItemContainer() {}

    public void addObservers(List<ItemViewInterface> observers) {
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
