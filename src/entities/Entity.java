package entities;

import entities.Enemies.Enemy;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String name;

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Enemy) {
            return this.getName().equals(((Enemy) obj).getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
