package gameio;

import game.Time;
import items.Consumables.Consumable;
import items.Deserializers.ConsumableDeserializer;
import entities.Deserializers.EnemyDeserializer;
import items.Deserializers.EquipableDeserializer;
import items.Deserializers.ItemDeserializer;
import entities.Enemies.Enemy;
import items.Equipables.Equipable;
import items.Item;
import maps.GameMap;
import maps.GameMapDeserializer;
import playercharacter.PlayerCharacter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class GameSerialization {
    public static GameMap readGameMapFromSave(String saveName)
            throws IOException {

        String savePath = GamePaths.SAVES_DIRECTORY + "/" + saveName;
        var gson = new GsonBuilder()
                .registerTypeAdapter(GameMap.class, new GameMapDeserializer())
                .registerTypeAdapter(Enemy.class, new EnemyDeserializer())
                .create();
        return gson.fromJson(
                String.join("\n", Files.readAllLines(Path.of(savePath + "/game_map.json"))),
                GameMap.class
        );
    }

    public static PlayerCharacter readPlayerCharacterFromSave(String saveName)
            throws IOException {

        String savePath = GamePaths.SAVES_DIRECTORY + "/" + saveName;
        var gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, new ItemDeserializer())
                .registerTypeAdapter(Equipable.class, new EquipableDeserializer())
                .registerTypeAdapter(Consumable.class, new ConsumableDeserializer())
                .create();
        PlayerCharacter playerCharacter = gson.fromJson(
                String.join("\n", Files.readAllLines(Path.of(savePath + "/player_character.json"))),
                PlayerCharacter.class
        );
        playerCharacter.setOwnerForAllItems();
        return playerCharacter;
    }

    public static long readElapsedTimeFromSave(String saveName) throws IOException {
        String savePath = GamePaths.SAVES_DIRECTORY + "/" + saveName;
        var gson = new Gson();
        return gson.fromJson(
                String.join("\n", Files.readAllLines(Path.of(savePath + "/elapsed_time.json"))),
                Long.class
        );
    }

    public static void createOrOverwriteSave(PlayerCharacter playerCharacter, GameMap gameMap,
                                             long startTime, String saveName)
            throws IOException {

        String saveDirName = GamePaths.SAVES_DIRECTORY + "/" + saveName;
        Path saveDir;
        if (Files.exists(Path.of(saveDirName))) {
            saveDir = Path.of(saveDirName);
        } else {
            saveDir = Files.createDirectory(Path.of(saveDirName));
        }
        var gson = new GsonBuilder().setPrettyPrinting().create();
        Path playerCharacterFilePath = Path.of(saveDir + "/player_character.json");
        Files.writeString(playerCharacterFilePath, gson.toJson(playerCharacter));

        Path gameMapFilePath = Path.of(saveDir + "/game_map.json");
        Files.writeString(gameMapFilePath, gson.toJson(gameMap));

        Path elapsedTimeFilePath = Path.of(saveDir + "/elapsed_time.json");
        Files.writeString(elapsedTimeFilePath, gson.toJson(Time.elapsedTimeFrom(startTime)));
    }

    public static List<String> avaiableSavesNames() throws IOException {
        try (Stream<Path> stream = Files.list(Path.of(GamePaths.SAVES_DIRECTORY))) {
            return stream
                    .filter(Files::isDirectory)
                    .map(path -> path.getFileName().toString())
                    .toList();
        }
    }

    public static void deleteSave(String saveName) throws IOException {
        Path savePath = Path.of(GamePaths.SAVES_DIRECTORY + "/" + saveName);
        if (avaiableSavesNames().contains(saveName)) {
            try (Stream<Path> stream = Files.walk(savePath)) {
                stream.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                        // deletes every file in the folder and then the folder itself
            }
        } else {
            throw new IOException("No save exists named \"" + saveName + "\"");
        }
    }

    public static boolean savesDirIsEmpty() throws IOException {
        try (DirectoryStream<Path> stream = Files
                .newDirectoryStream(Path.of(GamePaths.SAVES_DIRECTORY))) {
            return !stream.iterator().hasNext();
        }
    }

    public static boolean savesDirExists() throws IOException {
        return Files.exists(Path.of(GamePaths.SAVES_DIRECTORY));
    }
}
