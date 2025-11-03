package gameio;

import gameexceptions.GameIOException;
import gamerandom.GameRNG;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MapIO {
    public List<String> randomMapFileLines() throws IOException {
        try (Stream<Path> mapFilesStream = Files.list(Path.of(GamePaths.MAPS_DIRECTORY).toAbsolutePath())) {
            List<Path> mapFiles = mapFilesStream
                    .filter(path -> path.toString().endsWith(".gamemap"))
                    .toList();

            if (mapFiles.isEmpty()) {
                throw new GameIOException("No map files found!");
            } else {
                int mapFilesCount = mapFiles.size();
                int randomIndex = GameRNG.randomInRange(0, mapFilesCount - 1);
                Path randomMapFilePath = mapFiles.get(randomIndex);
                List<String> fileLines = Files.readAllLines(randomMapFilePath);
                List<String> map = parseMapFromFileLines(fileLines);
                return map;
            }
        }
    }

    private List<String> parseMapFromFileLines(List<String> lines) throws GameIOException {
        String mapBeginValue = MapParsingValues.MAP_BEGIN;
        String mapEndValue = MapParsingValues.MAP_END;
        if (lines.contains(mapBeginValue) && lines.contains(mapEndValue)
                && lines.indexOf(mapBeginValue) < lines.indexOf(mapEndValue)) {

            List<String> map = new ArrayList<>();
            int startIndex = lines.indexOf(mapBeginValue) + 1;
            int endIndex = lines.indexOf(mapEndValue);
            for (int i = startIndex; i < endIndex; i++) {
                map.add(lines.get(i));
            }
            map.removeIf(String::isBlank);
            return map;

        } else {
            throw new GameIOException("Map file is not in compatible format!");
        }
    }
}
