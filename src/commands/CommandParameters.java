package commands;

import game.GameTime;
import game.MutableBoolean;
import gameio.GameSerialization;
import maps.GameMap;
import mvc.views.characterviews.CharacterView;
import mvc.views.QuestView;
import playercharacter.PlayerCharacter;
import quests.Quest;

import java.util.List;

public record CommandParameters(QuestView questView, CharacterView characterView,
                                PlayerCharacter character, GameMap map, List<Quest> questList,
                                long startTime, String saveName,
                                MutableBoolean quit, GameSerialization gameSerialization,
                                GameTime gameTime, Object lock, String... args) {

}
