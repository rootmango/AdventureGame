package entities.ItemContainers;

import entities.Entity;
import items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ItemContainer extends Entity {
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
