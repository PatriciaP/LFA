package controller;

import model.Automata;
import model.State;
import model.Transition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BuildAutomata {

    private static ArrayList<State> states = new ArrayList<State>();

    private static ArrayList<Transition> transitions = new ArrayList<Transition>();

    /**
     * Method for read the json file
     * which will specify the AF and the
     * elimination order.
     * @param file
     */
    public static Automata readAndBuild(String file){

        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(file));

            JSONObject jsonObject = (JSONObject) obj;

            // automata array
            JSONArray af = (JSONArray) jsonObject.get("af");

            //getting the array of states
            JSONArray jsonState = (JSONArray) af.get(0);

            //adding the state into the array states
            for (Object aJsonState : jsonState) {
                states.add(new State(aJsonState.toString()));
            }

            //getting the alphabet array
            JSONArray jsonAlphabet = (JSONArray) af.get(1);

            //adding the array into the collection caracters
            Set<String> caracters = Collections.singleton(jsonAlphabet.toJSONString());

            //getting the array of transitions
            JSONArray jsonTransitions = (JSONArray) af.get(2);

            //counting how many arrays of transitions are
            Iterator iterator = jsonTransitions.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                count++;
                iterator.next();
            }

            //adding each transition into the array transitions
            for (int i = 0; i < count ; i++) {
                JSONArray jsonArray = (JSONArray) jsonTransitions.get(i);
                transitions.add(new Transition(jsonArray.get(0).toString(),
                        jsonArray.get(1).toString(),jsonArray.get(2).toString()));
            }

            // getting the array of initials states
            JSONArray initials = (JSONArray) af.get(3);

            // getting the array of finals states
            JSONArray finals = (JSONArray) af.get(4);

            //setting each state of the array states are initial and final
            for (State state : states) {
                for (Object initial : initials) {
                    if (state.getName().equals(initial.toString())) {
                        state.setInitialState(true);
                    }
                }
                for (Object aFinal : finals) {
                    if (state.getName().equals(aFinal.toString())) {
                        state.setFinalState(true);
                    }
                }
            }

            // getting the arry of elimination order
            JSONArray r = (JSONArray) jsonObject.get("r");

            //returning the specified automata
            return new Automata(states, caracters, transitions, r);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

}
