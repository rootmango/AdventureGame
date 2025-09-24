package mvc.views;

import quests.Quest;

public class QuestView extends MainView {
    public void showInfoStatus(Quest quest) {
        String statusSymbol = (quest.isCompleted()) ? "✔\uFE0F" : " ";
        System.out.println("[" + statusSymbol + "] " + quest.getName());
    }
}
