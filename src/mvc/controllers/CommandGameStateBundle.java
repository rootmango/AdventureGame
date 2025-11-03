package mvc.controllers;

import mvc.controllers.game.MutableBoolean;
import maps.GameMap;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.List;

public record CommandGameStateBundle(PlayerCharacter character, GameMap map,
                                     List<Quest> questList, String saveName,
                                     MutableBoolean quit) {
}
