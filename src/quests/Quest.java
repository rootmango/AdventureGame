package quests;

import java.io.Serializable;

public abstract class Quest implements Serializable {
    protected String name;
    protected String description;
    protected boolean completed = false;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public abstract boolean condition();

    protected abstract void onceCompleted();

    public void setOrUpdateCompleted() {
        if (!completed) {
            if (condition()) {
                completed = true;
                onceCompleted();
            }
        }
    }
}
