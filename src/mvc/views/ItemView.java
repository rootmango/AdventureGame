package mvc.views;

public class ItemView extends MainView {
    public void showAnotherItemAlreadyEquippedMessage() {
        outputln("An item is already equipped! Unequip it first to equip this one!");
    }
}
