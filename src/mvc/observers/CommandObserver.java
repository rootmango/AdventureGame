package mvc.observers;

import mvc.views.CommandView;

public class CommandObserver {
    protected final CommandView commandView;

    public CommandObserver(CommandView commandView) {
        this.commandView = commandView;
    }

    public void movedNorth() {
        commandView.showMoveNorthMessage();
    }

    public void movedWest() {
        commandView.showMoveWestMessage();
    }

    public void movedEast() {
        commandView.showMoveEastMessage();
    }

    public void movedSouth() {
        commandView.showMoveSouthMessage();
    }

    public void requestedNonExistingItemContainer() {
        commandView.showNoSuchItemContainerMessage();
    }

    public void requestedNonExistingEnemy() {
        commandView.showNoSuchEnemyMessage();
    }

    public void requestedHelp() {
        commandView.showHelpCommands();
    }

    public void savedSuccessfully(String saveName) {
        commandView.showSuccessfulSaveMessage(saveName);
    }

    public void errorSaving(Exception e) {
        commandView.showSaveErrorMessage(e);
    }
}
