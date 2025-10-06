package commands;

import game.GameTime;
import game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandViewInterface;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.ArrayList;
import java.util.List;

public class MapCommand extends Command {
    public MapCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public MapCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    /**
     * This constructor is used for when a {@code QuestsCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public MapCommand(PlayerCharacter character, GameMap map) {
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
                new GameTime(),
                new Object()
        ));
    }

    /**
     * This constructor is used for when a {@code QuestsCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public MapCommand(PlayerCharacter character, GameMap map, List<CommandViewInterface> commandViews) {
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
                new GameTime(),
                new Object()
        ), commandViews);
    }

    @Override
    public void execute() {
        int xCoordinate = character.getXCoordinate();
        int yCoordinate = character.getYCoordinate();
        characterView.showLocationOnMap(xCoordinate, yCoordinate, map);
    }
}
