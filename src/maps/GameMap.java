package maps;

import gameexceptions.CharacterNotFoundException;
import gameexceptions.UnrecognizedCharException;
import gameio.MapIO;
import gamerandom.RandomCommonEnemyGenerator;
import gamerandom.RandomItemContainerGenerator;
import mvc.views.enemyviews.EnemyObserver;
import mvc.views.itemviews.ItemObserver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static maps.Place.PLACES;

/**
 * A wrapper class for a 2d array that represents the 2d mvc.controllers.game map.
 */
public class GameMap implements Serializable {
    private final Place[][] placesArray;

    public GameMap(List<EnemyObserver> enemyObservers,
                   RandomCommonEnemyGenerator randomCommonEnemyGenerator,
                   RandomItemContainerGenerator randomItemContainerGenerator) throws IOException {
        placesArray = initializeNewRandomMap();
        fillMap(enemyObservers, randomCommonEnemyGenerator, randomItemContainerGenerator);
    }

    /**
     * Only used for gson's deserialization - gson requires a class to have an (either
     * public or protected) no-args constructor to automatically set all of its
     * fields during deserialization.
     */
    protected GameMap() {
        // set only to avoid compiler error.
        // this value doesn't matter, gson will still change it upon deserialization.
        placesArray = new Place[1][1];
    }

    public GameMap(Place[][] placesArray) {
        this.placesArray = placesArray;
    }

    public Place getFortress() {
        Place fortress = Arrays.stream(
                    this.fullMapStream()
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

    public Place[][] initializeNewRandomMap() throws IOException {
        MapIO mapIO = new MapIO();
        List<String> lines = mapIO.randomMapFileLines();
        Place[][] array = new Place[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                char c = lines.get(i).charAt(j);
                array[i][j] = new Place(j, i, c);
            }
        }

        return array;
    }

    public void fillMap(List<EnemyObserver> enemyObservers,
                             RandomCommonEnemyGenerator randomCommonEnemyGenerator,
                             RandomItemContainerGenerator randomItemContainerGenerator) {
        for (int i = 0; i < placesArray.length; i++) {
            for (int j = 0; j < placesArray[0].length; j++) {
                char placeChar = placesArray[i][j].getChar();
                if (PLACES.containsKey(placeChar)) {
                    placesArray[i][j].seedWithCommonEnemies(randomCommonEnemyGenerator);
                    placesArray[i][j].seedWithItemContainers(randomItemContainerGenerator);
                } else if (placeChar == 'F') {
                    placesArray[i][j].seedWithGoblinKing(enemyObservers);
                }
            }
        }
    }

    public void addEnemyObservers(List<EnemyObserver> observers) {
        for (int i = 0; i < placesArray.length; i++) {
            for (int j = 0; j < placesArray[0].length; j++) {
                Place place = placesArray[i][j];
                place.getEnemies().forEach(enemy -> enemy.addObservers(observers));
            }
        }
    }

    public void addItemObservers(List<ItemObserver> observers) {
        for (int i = 0; i < placesArray.length; i++) {
            for (int j = 0; j < placesArray[0].length; j++) {
                Place place = placesArray[i][j];
                place.getItemContainers()
                        .forEach(itemContainer -> itemContainer.addObservers(observers));
            }
        }
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
