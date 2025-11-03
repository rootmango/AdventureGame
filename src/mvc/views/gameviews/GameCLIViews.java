package mvc.views.gameviews;

import gameio.GameSerialization;
import mvc.views.GameMapView;
import mvc.views.GameSaveView;
import mvc.views.MainView;
import mvc.views.PromptYesNoValidation;
import mvc.views.characterviews.CharacterObserver;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandView;
import mvc.views.enemyviews.EnemyObserver;
import mvc.views.enemyviews.EnemyView;
import mvc.views.itemviews.ItemObserver;
import mvc.views.itemviews.ItemView;
import mvc.views.placeviews.PlaceObserver;
import mvc.views.placeviews.PlaceView;
import mvc.views.promptviews.PromptView;

import java.util.ArrayList;
import java.util.List;

public class GameCLIViews {
    protected final MainView mainView;
    protected final GameView gameView;
    protected final GameMapView mapView;
    protected final CharacterView characterView;
    protected final GameSaveView gameSaveView;
    protected final CommandView commandView;
    protected final PromptView promptView;

    protected final List<CharacterObserver> characterObservers;
    protected final List<PlaceObserver> placeObservers;
    protected final List<EnemyObserver> enemyObservers;
    protected final List<ItemObserver> itemObservers;
    protected final GameEventListener gameEventListener;

    protected final PromptYesNoValidation promptYesNoValidation;

    public GameCLIViews(GameSerialization gameSerialization) {
        mainView = new MainView();
        gameView = new GameView();
        mapView = new GameMapView();
        characterView = new CharacterView(mapView);
        gameSaveView = new GameSaveView(gameSerialization);
        commandView = new CommandView();
        promptView = new PromptView();

        characterObservers = new ArrayList<>(List.of(new CharacterView(mapView)));
        placeObservers = new ArrayList<>(List.of(new PlaceView()));
        enemyObservers = new ArrayList<>(List.of(new EnemyView()));
        itemObservers = new ArrayList<>(List.of(new ItemView()));
        gameEventListener = new GameView();

        promptYesNoValidation = new PromptYesNoValidation();
    }

    public MainView mainView() {
        return mainView;
    }

    public GameMapView mapView() {
        return mapView;
    }

    public CharacterView characterView() {
        return characterView;
    }

    public GameSaveView gameSaveView() {
        return gameSaveView;
    }

    public PromptYesNoValidation promptYesNoValidation() {
        return promptYesNoValidation;
    }

    public List<CharacterObserver> characterObservers() {
        return characterObservers;
    }

    public List<PlaceObserver> placeObservers() {
        return placeObservers;
    }

    public List<EnemyObserver> enemyObservers() {
        return enemyObservers;
    }

    public List<ItemObserver> itemObservers() {
        return itemObservers;
    }

    public GameEventListener gameEventListener() {
        return gameEventListener;
    }

    public PromptView promptView() {
        return promptView;
    }

    public GameView gameView() {
        return gameView;
    }

    public CommandView commandView() {
        return commandView;
    }
}
