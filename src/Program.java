import mvc.CommandController;
import mvc.PromptController;
import gameexceptions.CharacterNotFoundException;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.View;
import playercharacter.*;
import quests.*;
import game.Game;
import game.MutableBoolean;

import java.io.IOException;
import java.util.*;

public class Program {
    private static final View view = new View();

    private static final CommandController commandController = new CommandController();
    private static final Game game = new Game();
    private static final PromptController promptController = new PromptController();

    public static void main(String[] args) {
        try {

            PlayerCharacter character;
            GameMap map;
            String saveName = "";
            long startTime;

            boolean startNewGame = promptController.promptNewGame();

            if (startNewGame) {
                saveName = promptController.promptNewSaveName();
                map = new GameMap();
                String characterType = promptController.promptCharacterType();
                character = new PlayerCharacter(characterType);
                character.findCharacterAndSetCoordinates(map);
                startTime = System.currentTimeMillis();
                GameSerialization.createOrOverwriteSave(character, map, startTime, saveName);
                view.outputln("New save \"" + saveName + "\" created successfully!");

                commandController.printHelpCommands();
            } else {
                saveName = promptController.promptLoadGame();
                map = GameSerialization.readGameMapFromSave(saveName);
                character = GameSerialization.readPlayerCharacterFromSave(saveName);
                long elapsedTime = GameSerialization.readElapsedTimeFromSave(saveName);
                startTime = System.currentTimeMillis() - elapsedTime;
                // "adds" the elapsed time from the previous session to the current session.
                // By subtracting the number of elapsed milliseconds from the current time,
                // we're making it as if the current session started X amount of milliseconds
                // earlier.
                view.outputln("Loaded save \"" + saveName + "\"");
            }

            MutableBoolean won = new MutableBoolean(false);
            MutableBoolean dead = new MutableBoolean(false);
            MutableBoolean quit = new MutableBoolean(false);
            List<Quest> questList = new ArrayList<>(List.of(
                    new CollectXPForFortress(character, map.getFortress(map)),
                    new DefeatTheGoblinKing(won, map.getFortress(map))
            ));

            questList.forEach(Quest::setOrUpdateCompleted);
            // necessary if we are loading a game and the conditions are already met
            view.outputln("Quests:");
            commandController.quests(questList);
            view.outputln();
            commandController.map(character, map);
            view.outputln(
                    "Try moving to a desired direction (for example, \"move north\") or looking around " +
                            "(\"look\")");

            game.gameLoop(character, map, questList, won, dead, quit, startTime, saveName, commandController);
        } catch (IOException e) {
            view.outputln("Error: " + e.getMessage());
        } catch (CharacterNotFoundException e) {
            view.outputln("Error in map file: " + e.getMessage());
        }
    }
}
