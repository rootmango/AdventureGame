package commands;

import mvc.controllers.game.GameTimeUtils;
import mvc.controllers.game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandEventListener;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.ArrayList;

public class MapCommand extends Command {
    public MapCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    /**
     * This constructor is used for when a {@code QuestsCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public MapCommand(PlayerCharacter character, GameMap map, CommandEventListener commandEventListener) {
        super(new CommandParameters(
                new QuestView(),
                new CharacterView(new GameMapView()),
                character,
                map,
                new ArrayList<Quest>(),
                0,
                "",
                new MutableBoolean(false),
                new GameSerialization(),
                new GameTimeUtils(),
                new Object()
        ), commandEventListener);
    }

    @Override
    public void execute() {
        int xCoordinate = character.getXCoordinate();
        int yCoordinate = character.getYCoordinate();
        characterView.showLocationOnMap(xCoordinate, yCoordinate, map);
    }
}
