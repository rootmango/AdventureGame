package mvc.views.placeviews;

import maps.Place;
import mvc.views.MainView;

public class PlaceView extends MainView implements PlaceViewInterface {
    public void showInaccessibleMessage(Place place) {
        outputln(place.getInaccessibleMessage());
    }

    public void showEntryMessage(Place place) {
        outputln(place.getEntryMessage());
    }
}
