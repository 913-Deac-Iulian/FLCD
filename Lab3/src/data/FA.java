package data;

import java.util.ArrayList;

public class FA {
    private final ArrayList<Transition> transitions;
    private final String[] states;
    private final String[] initialStates;
    private final String[] finalStates;
    private final String[] alphabet;

    public FA(ArrayList<Transition> transitions, String[] states, String[] initialStates, String[] finalStates, String[] alphabet) {
        this.transitions = transitions;
        this.states = states;
        this.initialStates = initialStates;
        this.finalStates = finalStates;
        this.alphabet = alphabet;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public String[] getStates() {
        return states;
    }

    public String[] getInitialStates() {
        return initialStates;
    }

    public String[] getFinalStates() {
        return finalStates;
    }

    public String[] getAlphabet() {
        return alphabet;
    }
}
