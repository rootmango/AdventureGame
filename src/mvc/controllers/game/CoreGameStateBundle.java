package mvc.controllers.game;

import maps.GameMap;
import playercharacter.PlayerCharacter;

public record CoreGameStateBundle(PlayerCharacter character, GameMap map,
                                  String saveName, long startTime) {
}
