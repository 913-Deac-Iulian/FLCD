package data;

public class Transition {
    private State state1;
    private State state2;
    private String value;

    public Transition(State state1, State state2, String value) {
        this.state1 = state1;
        this.state2 = state2;
        this.value = value;
    }

    public State getState1() {
        return state1;
    }

    public void setState1(State state1) {
        this.state1 = state1;
    }

    public State getState2() {
        return state2;
    }

    public void setState2(State state2) {
        this.state2 = state2;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "state1=" + state1.getName() +
                ", state2=" + state2.getName() +
                ", value='" + value + '\'' +
                '}';
    }
}
