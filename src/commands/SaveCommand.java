package commands;

import mvc.controllers.game.GameTimeUtils;
import mvc.controllers.game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.characterviews.CharacterView;
import mvc.views.GameMapView;
import mvc.views.QuestView;
import mvc.views.commandviews.CommandEventListener;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.io.IOException;
import java.util.ArrayList;

public class SaveCommand extends Command {
    public SaveCommand(CommandParameters commandParams, CommandEventListener commandEventListener) {
        super(commandParams, commandEventListener);
    }

    /**
     * This constructor is used for when a {@code SaveCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public SaveCommand(PlayerCharacter character, GameMap map, long startTime, String saveName,
                       GameSerialization gameSerialization, GameTimeUtils gameTimeUtils,
                       CommandEventListener commandEventListener) {
        super(new CommandParameters(
                new QuestView(),
                new CharacterView(new GameMapView()),
                character,
                map,
                new ArrayList<Quest>(),
                startTime,
                saveName,
                new MutableBoolean(false),
                gameSerialization,
                gameTimeUtils,
                new Object()
        ), commandEventListener);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            try {
                gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTimeUtils);
                commandEventListener.showSuccessfulSaveMessage(saveName);
            } catch (IOException e) {
                commandEventListener.showSaveErrorMessage(e);
            }
        }
    }
}
