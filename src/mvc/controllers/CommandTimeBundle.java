package mvc.controllers;

import mvc.controllers.game.GameTimeUtils;

public record CommandTimeBundle(long startTime, GameTimeUtils gameTimeUtils) {
}
