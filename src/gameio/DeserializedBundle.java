package gameio;

import maps.GameMap;
import playercharacter.PlayerCharacter;

public record DeserializedBundle(PlayerCharacter character, GameMap map, long elapsedTime) {
}
