import mvc.controllers.game.*;
import gameexceptions.CharacterNotFoundException;
import gameio.GameSerialization;
import mvc.controllers.CommandController;
import mvc.controllers.PromptController;
import mvc.views.MainView;
import mvc.views.gameviews.GameCLIViews;

import java.io.IOException;

public class Program {

    public static void main(String[] args) {
        var gameSerialization = new GameSerialization();
        var gameViews = new GameCLIViews(gameSerialization);
        MainView mainView = gameViews.mainView();
        try {
            var promptController = new PromptController(gameViews);
            CoreGameStateBundle coreGameStateBundle = promptController
                    .promptInitializeGameState(gameViews, gameSerialization);

            var commandController = new CommandController();

            var game = new Game(gameViews, coreGameStateBundle, commandController, gameSerialization);
            game.gameLoop();
        } catch (IOException e) {
            mainView.outputln("Error: " + e.getMessage());
        } catch (CharacterNotFoundException e) {
            mainView.outputln("Error in map file: " + e.getMessage());
        }
    }
}
