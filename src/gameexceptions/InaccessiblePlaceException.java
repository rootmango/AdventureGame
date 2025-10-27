package gameexceptions;

import maps.Place;

public class InaccessiblePlaceException extends RuntimeException {
    public InaccessiblePlaceException(Place place) {
        super(place.getInaccessibleMessage());
    }

    public InaccessiblePlaceException(String message) {
        super(message);
    }
}
