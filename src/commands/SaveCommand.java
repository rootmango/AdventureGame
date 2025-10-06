package commands;

import game.GameTime;
import game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.characterviews.CharacterView;
import mvc.views.GameMapView;
import mvc.views.QuestView;
import mvc.views.commandviews.CommandViewInterface;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveCommand extends Command {
    public SaveCommand(CommandParameters commandParams) {
        super(commandParams);
    }

    public SaveCommand(CommandParameters commandParams, List<CommandViewInterface> commandViews) {
        super(commandParams, commandViews);
    }

    /**
     * This constructor is used for when a {@code SaveCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public SaveCommand(PlayerCharacter character, GameMap map, long startTime, String saveName,
                       GameSerialization gameSerialization, GameTime gameTime) {
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
                gameTime,
                new Object()
        ));
    }

    /**
     * This constructor is used for when a {@code SaveCommand} needs to be created outside
     * of {@code CommandFactory}. The arguments are the parameters the command needs, while
     * the rest of the fields are assigned to newly-initialized objects (since every field in
     * {@code Command} is {@code final}).
     */
    public SaveCommand(PlayerCharacter character, GameMap map, long startTime, String saveName,
                       GameSerialization gameSerialization, GameTime gameTime,
                       List<CommandViewInterface> commandViews) {
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
                gameTime,
                new Object()
        ), commandViews);
    }

    @Override
    public void execute() {
        synchronized (lock) {
            try {
                gameSerialization.createOrOverwriteSave(character, map, startTime, saveName, gameTime);
                commandViews.forEach(view -> view.showSuccessfulSaveMessage(saveName));
            } catch (IOException e) {
                commandViews.forEach(view -> view.showSaveErrorMessage(e));
            }
        }
    }
}
