package mvc.views;

import entities.Entity;
import maps.GameMap;
import maps.Place;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameMapView extends MainView {
    /**
     * Returns a {@code String} of the names of all the entities contained in this {@code Place},
     * or, if it's empty, a {@code String} indicating that.
     */
    public String entitiesInfoAt(GameMap map, int xCoordinate, int yCoordinate) {
        Place place = map.at(xCoordinate, yCoordinate);
        if (place.getItemContainers().isEmpty() && place.getEnemies().isEmpty()) {
            return "There's nothing here!";
        } else {
            return Stream.concat(place.getItemContainers().stream(), place.getEnemies().stream())
                    .map(Entity::getName)
                    .collect(Collectors.joining("\n"));
        }
    }
}
