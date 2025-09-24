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

//    @Override
//    public String infoStatus() {
//        int XP = Math.min(character.getXP(), necessaryXP);
//        // so that the quest won't show more XP than what is needed
//        // (if the character has 600 XP but the quest requires 500 XP,
//        // the quest will show 500/500 XP instead of 600/500 XP)
//
//        return super.infoStatus() + " ("
//                + String.format("%,d", XP) + "/"
//                + String.format("%,d", necessaryXP)
//                + " XP)";
//    }
}
