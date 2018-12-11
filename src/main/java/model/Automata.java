package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Automata {

    ArrayList<State> states = new ArrayList<State>();

    Set<String> alphabet = new HashSet<String>();

    ArrayList<Transition> transitions = new ArrayList<Transition>();

    ArrayList<String> eliminationOrder = new ArrayList<String>();


    static State state;

    static Transition transition;


    public Automata() {
    }

    public Automata(ArrayList<State> states, Set<String> alphabet,
                    ArrayList<Transition> transitions, ArrayList<String> eliminationOrder) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.eliminationOrder = eliminationOrder;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        Automata.state = state;
    }

    public static Transition getTransition() {
        return transition;
    }

    public static void setTransition(Transition transition) {
        Automata.transition = transition;
    }

    public ArrayList<String> getEliminationOrder() {
        return eliminationOrder;
    }

    public void setEliminationOrder(ArrayList<String> eliminationOrder) {
        this.eliminationOrder = eliminationOrder;
    }


    @Override
    public String toString() {
        return "Automata{" +
                "\nStates=" + states +
                ",\nTransitions=" + transitions +
                ",\nAlphabet=" + alphabet +
                ",\nElimination Order=" + eliminationOrder +
                '}';
    }
}
