package quests;

import maps.Place;
import playercharacter.PlayerCharacter;

public class CollectXPForFortress extends Quest {
    private final PlayerCharacter character;
    private final int necessaryXP;
    private final Place fortress;

    public CollectXPForFortress(PlayerCharacter character, Place fortress) {
        necessaryXP = 200;
        name = "Collect " + necessaryXP + " XP To Enter Into Fortress";
        description = "Collect " + necessaryXP + " XP To Enter Into Fortress";
        this.character = character;
        this.fortress = fortress;
    }

    @Override
    public boolean condition() {
        return character.getXP() >= necessaryXP;
    }

    @Override
    protected void onceCompleted() {
        fortress.unlock();
    }
}
