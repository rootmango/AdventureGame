package quests;

import mvc.controllers.game.MutableBoolean;
import maps.Place;

public class DefeatTheGoblinKing extends Quest {
    private final MutableBoolean won;
    private final Place fortress;

    public DefeatTheGoblinKing(MutableBoolean won, Place fortress) {
        name = "Defeat the Goblin King";
        description = "Defeat the Goblin King";
        this.won = won;
        this.fortress = fortress;
    }

    @Override
    protected void onceCompleted() {
        won.setValue(true);
    }

    @Override
    public boolean condition() {
        return !fortress.containsBossEnemy();
    }
}
