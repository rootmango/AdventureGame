package maps;

import gameexceptions.CharacterNotFoundException;
import gameexceptions.UnrecognizedCharException;
import gameio.MapIO;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * A wrapper class for a 2d array that represents the 2d game map.
 */
public class GameMap implements Serializable {
    private final Place[][] placesArray;

    public GameMap() throws IOException {
        placesArray = fillMap();
    }

    public GameMap(Place[][] placesArray) {
        this.placesArray = placesArray;
    }

    public Place getFortress(GameMap map) {
        Place fortress = Arrays.stream(
                    map.fullMapStream()
                        .filter(x -> Arrays.stream(x).anyMatch(Place::isFortress))
                        .findFirst()
                        .orElseThrow(UnrecognizedCharException::new)
                        // the row where "Fortress" is contained, converted to a stream of places
                )
                .filter(Place::isFortress)
                .findFirst()
                // returns the "Fortress" place itself
                .orElseThrow(() -> new CharacterNotFoundException("Fortress not found"));

        return fortress;
    }

    public Place[][] fillMap() throws IOException {
        List<String> lines = MapIO.randomMap();
        Place[][] array = new Place[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                array[i][j] = new Place(j, i, c);
            }
        }

        return array;
    }

    public Place at(int xCoordinate, int yCoordinate) {
        return placesArray[yCoordinate][xCoordinate];
    }

    public Place[][] getFullMap() {
        return placesArray;
    }

    public Stream<Place[]> fullMapStream() {
        return Arrays.stream(placesArray);
    }

}
