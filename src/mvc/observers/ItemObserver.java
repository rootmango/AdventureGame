package mvc.observers;

import mvc.views.ItemView;

public class ItemObserver {
    protected final ItemView itemView;

    public ItemObserver(ItemView itemView) {
        this.itemView = itemView;
    }

    public void failedAttemptEquipItem() {
        itemView.showAnotherItemAlreadyEquippedMessage();
    }
}
