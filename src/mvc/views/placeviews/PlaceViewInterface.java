package mvc.views.placeviews;

import maps.Place;

public interface PlaceViewInterface {
    void showInaccessibleMessage(Place place);

    void showEntryMessage(Place place);
}
