package mvc.controllers;

import commands.Command;
import commands.CommandFactory;
import commands.CommandParameters;
import mvc.controllers.game.GameTimeUtils;
import mvc.controllers.game.MutableBoolean;
import gameexceptions.EmptyCommandNameException;
import gameexceptions.InsufficientCommandArgsException;
import gameexceptions.InvalidCommandArgsException;
import gameexceptions.InvalidCommandNameException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.*;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandView;
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

    public void handleCommandInput(Object lock,
                                   CommandGameStateBundle commandGameStateBundle,
                                   CommandViewsBundle commandViewsBundle,
                                   CommandTimeBundle commandTimeBundle,
                                   GameSerialization gameSerialization) {

        PlayerCharacter character = commandGameStateBundle.character();
        GameMap map = commandGameStateBundle.map();
        List<Quest> questList = commandGameStateBundle.questList();
        String saveName = commandGameStateBundle.saveName();
        MutableBoolean quit = commandGameStateBundle.quit();

        CommandView commandView = commandViewsBundle.commandView();
        PromptView promptView = commandViewsBundle.promptView();
        QuestView questView = commandViewsBundle.questView();
        CharacterView characterView = commandViewsBundle.characterView();

        long startTime = commandTimeBundle.startTime();
        GameTimeUtils gameTimeUtils = commandTimeBundle.gameTimeUtils();

        promptView.showPromptChar();

        String[] inputArgs = commandView.userInputString().split(" ");

        if (inputArgs.length > 0) {
            String commandName = getCommandFromInputArgs(inputArgs);
            String[] args = getCommandArgsFromInputArgs(inputArgs);

            var commandParams = new CommandParameters(questView, characterView,
                    character, map, questList, startTime, saveName,
                    quit, gameSerialization, gameTimeUtils, lock, args);

            try {
                var commandFactory = new CommandFactory();
                Command command = commandFactory
                        .createCommand(commandName, commandParams, commandView);
                command.execute();
            } catch (InvalidCommandNameException e) {
                commandView.outputln("Invalid command name!");
            } catch (EmptyCommandNameException e) {
                commandView.outputln("Command name cannot be empty!");
            } catch (InsufficientCommandArgsException e) {
                commandView.showHelpCommands();
            } catch (InvalidCommandArgsException e) {
                commandView.outputln(e.getMessage());
            }
        }

    }
}
