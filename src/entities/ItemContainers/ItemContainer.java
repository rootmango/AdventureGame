package entities.ItemContainers;

import entities.Entity;
import items.Item;
import mvc.observers.ItemObserver;
import mvc.views.ItemView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ItemContainer extends Entity {
    protected final ItemObserver itemObserver;

    public ItemContainer(ItemObserver itemObserver) {
        this.itemObserver = itemObserver;
    }

    protected ItemContainer() {
        // set only to avoid compiler error.
        // this value doesn't matter, gson will still change it upon deserialization.
        itemObserver = new ItemObserver(new ItemView());
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
