package commands;

import game.GameTime;
import game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.observers.CommandObserver;
import mvc.views.CharacterView;
import mvc.views.MainView;
import mvc.views.QuestView;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    protected final QuestView questView;
    protected final CharacterView characterView;
    protected final CommandObserver commandObserver;
    protected final PlayerCharacter character;
    protected final GameMap map;
    protected final List<Quest> questList;
    protected final long startTime;
    protected final String saveName;
    protected final MutableBoolean quit;
    protected final GameSerialization gameSerialization;
    protected final GameTime gameTime;
    protected final Object lock;
    protected final String[] args;

    public abstract void execute();

    public Command(CommandParameters commandParams) {
        this.questView = commandParams.questView();
        this.characterView = commandParams.characterView();
        this.commandObserver = commandParams.commandObserver();
        this.character = commandParams.character();
        this.map = commandParams.map();
        this.questList = commandParams.questList();
        this.startTime = commandParams.startTime();
        this.saveName = commandParams.saveName();
        this.quit = commandParams.quit();
        this.gameSerialization = commandParams.gameSerialization();
        this.gameTime = commandParams.gameTime();
        this.lock = commandParams.lock();
        this.args = commandParams.args();
    }

    protected final String getSubjectNameFromCommandArgs(String[] commandArgs) {
        return String.join(" ", Arrays.copyOfRange(commandArgs, 0, commandArgs.length));
        // joins together the rest of the line in case of multi-word names
        // so that, if the command is "attack goblin king", the method will return "goblin king"
    }
}
