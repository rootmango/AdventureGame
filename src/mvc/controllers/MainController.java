package mvc.controllers;

import commands.Command;
import commands.CommandFactory;
import commands.CommandParameters;
import game.GameLoopCoreParams;
import game.GameTime;
import game.MutableBoolean;
import gameexceptions.InvalidCommandNameException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.observers.CommandObserver;
import mvc.views.*;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.Arrays;
import java.util.List;

public class MainController {
    public String getCommandFromInputArgs(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return "";
        } else {
            return inputArgs[0];
        }
    }

    public String[] getCommandArgsFromInputArgs(String[] inputArgs) {
        return Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
    }

    public void handleCommandInput(MainView mainView, Object lock,
                                   GameLoopCoreParams gameLoopCoreParams,
                                   long startTime, MutableBoolean quit, PromptView promptView,
                                   QuestView questView, CharacterView characterView,
                                   CommandObserver commandObserver, GameSerialization gameSerialization,
                                   GameTime gameTime) {

        PlayerCharacter character = gameLoopCoreParams.character();
        GameMap map = gameLoopCoreParams.map();
        List<Quest> questList = gameLoopCoreParams.questList();
        String saveName = gameLoopCoreParams.saveName();

        promptView.showPromptChar();

        String[] inputArgs = mainView.userInputString().split(" ");

        if (inputArgs.length > 0) {
            String commandName = getCommandFromInputArgs(inputArgs);
            String[] args = getCommandArgsFromInputArgs(inputArgs);

            var commandParams = new CommandParameters(questView, characterView,
                    commandObserver, character, map, questList, startTime, saveName,
                    quit, gameSerialization, gameTime, lock, args);

            try {
                var commandFactory = new CommandFactory();
                Command command = commandFactory.createCommand(commandName, commandParams);
                command.execute();
            } catch (InvalidCommandNameException e) {
                mainView.outputln("Error: " + e.getMessage());
            }
        }

    }
}
