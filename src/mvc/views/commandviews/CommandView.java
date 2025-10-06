package mvc.views.commandviews;

import mvc.views.MainView;

public class CommandView extends MainView implements CommandViewInterface {
    public void showMoveNorthMessage() {
        outputln("Moving north");
    }

    public void showMoveWestMessage() {
        outputln("Moving west");
    }

    public void showMoveEastMessage() {
        outputln("Moving east");
    }

    public void showMoveSouthMessage() {
        outputln("Moving south");
    }

    public void showNoSuchItemContainerMessage() {
        outputln("No such item container here");
    }

    public void showNoSuchEnemyMessage() {
        outputln("No such enemy here");
    }

    public void showHelpCommands() {
        outputln();
        outputln("Available commands you can use:\n");

        outputln("move [north | east | west | south] - moves to the desired direction");
        outputln("map - shows your current coordinates");
        outputln("look - shows what's around you");
        outputln("inventory - shows all the items you currently have in your inventory");
        outputln("take [item container] - takes an item from an item container "
                + "(for example, \"take chest\")");
        outputln("stats - shows you character's stats");
        outputln("use [item] - uses an item from your inventory. If it's a consumable "
                + "(for example, a potion), consumes it, and if it's an equipable "
                + "(for  example, weapons), equips it.");
        outputln("attack [enemy] - attacks a nearby enemy (for example, \"attack goblin\")");
        outputln("unequip - if you have equipped an item, unequips it and "
                + "puts it back in your inventory");
        outputln("equipped - shows your equipped item, if you have one");
        outputln("quests - shows the quests you have and your progress in them");
        outputln("save - saves your current progress. Note that your progress is periodically "
                + "autosaved as well");
        outputln("help - shows this list of commands if you forget them");

        outputln();
    }

    public void showSuccessfulSaveMessage(String saveName) {
        outputln("Saved to \"" + saveName + "\"");
    }

    public void showSaveErrorMessage(Exception e) {
        outputln("Error saving: " + e.getMessage());
    }
}
