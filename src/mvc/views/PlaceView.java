package mvc.views;

import maps.Place;

public class PlaceView extends MainView {
    public void showInaccessibleMessage(Place place) {
        outputln(place.getInaccessibleMessage());
    }

    public void showEntryMessage(Place place) {
        outputln(place.getEntryMessage());
    }
}
