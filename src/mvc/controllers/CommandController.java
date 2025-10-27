package mvc.controllers;

import commands.Command;
import commands.CommandFactory;
import commands.CommandParameters;
import game.GameLoopCoreParams;
import game.GameTime;
import game.MutableBoolean;
import gameexceptions.EmptyCommandNameException;
import gameexceptions.InsufficientCommandArgsException;
import gameexceptions.InvalidCommandArgsException;
import gameexceptions.InvalidCommandNameException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandEventListener;
import mvc.views.promptviews.PromptView;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.Arrays;
import java.util.List;

public class CommandController {
    protected String getCommandFromInputArgs(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return "";
        } else {
            return inputArgs[0];
        }
    }

    protected String[] getCommandArgsFromInputArgs(String[] inputArgs) {
        return Arrays.copyOfRange(inputArgs, 1, inputArgs.length);
    }

    public void handleCommandInput(MainView mainView, Object lock,
                                   GameLoopCoreParams gameLoopCoreParams,
                                   long startTime, MutableBoolean quit, PromptView promptView,
                                   QuestView questView, CharacterView characterView,
                                   GameSerialization gameSerialization,
                                   List<CommandEventListener> commandViews, GameTime gameTime) {

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
                    character, map, questList, startTime, saveName,
                    quit, gameSerialization, gameTime, lock, args);

            try {
                var commandFactory = new CommandFactory();
                Command command = commandFactory.createCommand(commandName, commandParams, commandViews);
                command.execute();
            } catch (InvalidCommandNameException e) {
                mainView.outputln("Invalid command name!");
            } catch (EmptyCommandNameException e) {
                mainView.outputln("Command name cannot be empty!");
            } catch (InsufficientCommandArgsException e) {
                commandViews.forEach(CommandEventListener::showHelpCommands);
            } catch (InvalidCommandArgsException e) {
                mainView.outputln(e.getMessage());
            }
        }

    }
}
