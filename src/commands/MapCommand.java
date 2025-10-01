package commands;

import game.GameTime;
import game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import maps.Place;
import mvc.observers.CharacterObserver;
import mvc.observers.CommandObserver;
import mvc.observers.PlaceObserver;
import mvc.views.*;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.ArrayList;
import java.util.List;

public class MapCommand extends Command {
    public MapCommand(CommandParameters commandParams) {
        super(commandParams);
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
                new CommandObserver(new CommandView()),
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

    @Override
    public void execute() {
        character.locationOnMap(map);
    }
}
