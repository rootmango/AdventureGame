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

public class QuestsCommand extends Command {
    public QuestsCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    /**
     * This constructor is used for when a {@code QuestsCommand} needs to be created outside
     * of {@code CommandFactory}. The argument is the parameter the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public QuestsCommand(List<Quest> questList) {
        super(new CommandParameters(
                new QuestView(),
                new CharacterView(new GameMapView()),
                new CommandObserver(new CommandView()),
                new PlayerCharacter("warrior", new MainView(), new GameMapView(),
                                    new CharacterObserver(new CharacterView(new GameMapView())),
                                    new PlaceObserver(new PlaceView())),
                new GameMap(new Place[0][0]),
                questList,
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
        questList.forEach(questView::showInfoStatus);
    }
}
