package mvc.observers;

import maps.Place;
import mvc.views.PlaceView;

public class PlaceObserver {
    protected final PlaceView placeView;

    public PlaceObserver(PlaceView placeView) {
        this.placeView = placeView;
    }

    public void attemptedEntryInaccessiblePlace(Place place) {
        placeView.showInaccessibleMessage(place);
    }

    public void enteredPlace(Place place) {
        placeView.showEntryMessage(place);
    }
}
