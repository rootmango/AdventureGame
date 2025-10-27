package mvc.views.placeviews;

import maps.Place;
import mvc.views.MainView;

public class PlaceView extends MainView implements PlaceObserver {
    public void showEntryMessage(Place place) {
        outputln(place.getEntryMessage());
    }
}
