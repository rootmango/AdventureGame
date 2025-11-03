package commands;

import mvc.controllers.game.GameTimeUtils;
import mvc.controllers.game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import maps.Place;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandEventListener;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.List;

public class QuestsCommand extends Command {
    public QuestsCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    /**
     * This constructor is used for when a {@code QuestsCommand} needs to be created outside
     * of {@code CommandFactory}. The argument is the parameter the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public QuestsCommand(List<Quest> questList, CommandEventListener commandEventListener) {
        super(new CommandParameters(
                new QuestView(),
                new CharacterView(new GameMapView()),
                new PlayerCharacter("warrior"),
                new GameMap(new Place[0][0]),
                questList,
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
        questList.forEach(questView::showInfoStatus);
    }
}
