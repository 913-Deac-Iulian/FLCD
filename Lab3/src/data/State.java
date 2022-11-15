package data;

import java.util.Objects;

public class State {
    String name;
    boolean isInitial;
    boolean isFinal;

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return isInitial == state.isInitial && isFinal == state.isFinal && Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isInitial, isFinal);
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", isInitial=" + isInitial +
                ", isFinal=" + isFinal +
                '}';
    }
}
