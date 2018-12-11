package model;

import java.util.HashMap;

public class State {

    private String name;

    private boolean finalState;

    private boolean initialState;

    public static HashMap<State, Transition> states = new HashMap<State, Transition>();


    public State() {
    }


    public State(String name, boolean finalState, boolean initialState) {
        this.name = name;
        this.finalState = finalState;
        this.initialState = initialState;
    }

    public State(String name){
        this.name = name;
        this.finalState = false;
        this.initialState = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }

    public boolean isInitialState() {
        return initialState;
    }

    public void setInitialState(boolean initialState) {
        this.initialState = initialState;
    }




    @Override
    public String toString() {
        return "\nState{" +
                "name='" + name + '\'' +
                ", finalState=" + finalState +
                ", initialState=" + initialState +
                '}';
    }
}
