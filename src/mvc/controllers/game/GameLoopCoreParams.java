package mvc.controllers.game;

import maps.GameMap;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.List;

/**
 * Bundles the game loop parameters related to the {@code character}, {@code map}, {@code questList}
 * and {@code saveName} to avoid passing too many arguments.
 */
public record GameLoopCoreParams(PlayerCharacter character, GameMap map, List<Quest> questList,
                                 String saveName) {
}
