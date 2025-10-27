package mvc.views.commandviews;

public interface CommandEventListener {
    void showMoveNorthMessage();

    void showMoveWestMessage();

    void showMoveEastMessage();

    void showMoveSouthMessage();

    void showNoSuchItemContainerMessage();

    void showNoSuchEnemyMessage();

    void showHelpCommands() ;

    void showSuccessfulSaveMessage(String saveName);

    void showSaveErrorMessage(Exception e);
}
