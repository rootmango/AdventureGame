package mvc.views.itemviews;

import mvc.views.MainView;

public class ItemView extends MainView implements ItemViewInterface {
    public void onAnotherItemAlreadyEquipped() {
        outputln("An item is already equipped! Unequip it first to equip this one!");
    }
}
