package mvc.controllers.game;

/**
 * Bundles the mvc.controllers.game loop {@code MutableBoolean} parameters to avoid passing too many arguments.
 */
public record GameLoopMutableBooleans(MutableBoolean won, MutableBoolean dead, MutableBoolean quit) {
}
