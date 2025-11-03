package mvc.controllers;

import mvc.views.QuestView;
import mvc.views.characterviews.CharacterView;
import mvc.views.commandviews.CommandEventListener;
import mvc.views.commandviews.CommandView;
import mvc.views.promptviews.PromptView;

public record CommandViewsBundle(CommandView commandView, PromptView promptView,
                                 QuestView questView, CharacterView characterView) {
}
