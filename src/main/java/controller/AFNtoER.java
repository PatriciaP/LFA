package controller;

import graphviz.ConvertToGraph;
import model.Automata;
import model.State;
import model.Transition;

import java.util.ArrayList;
import java.util.List;

public class AFNtoER {

    /**                        STEP 1
     * First step of the algorithm to find an equivalent ER of an AFN
     * Creating only one initial and final state.
     * @param afn
     * @return
     */
    public static Automata addingNewStates(Automata afn){

        // creating the new initial and final state: i,f
        afn.getStates().add(new State("i",false,true));
        afn.getStates().add(new State("f",true,false));

        //creating the new transitions for the new initial and final state
        for (int i = 0; i < afn.getStates().size() ; i++) {

            if(afn.getStates().get(i).isInitialState() &&
                    !afn.getStates().get(i).getName().equals("i")){

                afn.getStates().get(i).setInitialState(false);
                afn.getTransitions().add(new Transition("i","λ",afn.getStates().get(i).getName()));
            }

            if(afn.getStates().get(i).isFinalState()&&
                    !afn.getStates().get(i).getName().equals("f")){
                afn.getStates().get(i).setFinalState(false);
                afn.getTransitions().add(new Transition(afn.getStates().get(i).getName(),"λ","f"));

            }
        }

        return afn;
    }


    /**                     STEP 2
     * Substitutions of transitions conjunctions
     * @param afn
     * @return
     */
    public static Automata transitionsSubstitutions(Automata afn){

        for (int i = 0; i < afn.getTransitions().size(); i++) {

            Transition transition = afn.getTransitions().get(i);

            String origin = transition.getOriginState();
            String destiny = transition.getDestinyState();

            // treating the conjunction
            for (int j = i + 1; j < afn.getTransitions().size(); j++) {
                if (afn.getTransitions().get(j).getOriginState().equals(origin)
                        && afn.getTransitions().get(j).getDestinyState().equals(destiny)) {

                    transition.setLabelTransition(transition.getLabelTransition()+"+"+
                            afn.getTransitions().get(j).getLabelTransition());
                    afn.getTransitions().remove(j);
                }
            }

        }

        return afn;
    }


    public static void stateElimination(Automata afn){

        for (int i = 0; i < afn.getEliminationOrder().size() ; i++) {

            String state = afn.getEliminationOrder().get(i);

            outputSimulate(afn,"stateElimination_"+state, "state"+state, state);

        }

    }


    public static Automata convertToER(Automata a) {

        CharSequence expression;

        for (int i = 0; i < a.getEliminationOrder().size(); i++) {
            //state to be removed
            String stateRemove = a.getEliminationOrder().get(i);

            for (int j = 0; j < a.getTransitions().size(); j++) {

                // OTHER STATE -> REMOVED STATE
                if(!a.getTransitions().get(j).getOriginState().equals(stateRemove) &&
                        a.getTransitions().get(j).getDestinyState().equals(stateRemove)){


                    for (int k = 0; k <a.getTransitions().size() ; k++) {


                        //getting the Kleene Star (*)
                        if (a.getTransitions().get(k).getOriginState().equals(stateRemove) &&
                                a.getTransitions().get(k).getDestinyState().equals(stateRemove)){

                            expression = a.getTransitions().get(k).getLabelTransition()+"*";
                            a.getTransitions().remove(a.getTransitions().get(k));
                            a.getTransitions().get(j).setLabelTransition(a.getTransitions().get(j).getLabelTransition()+""+expression);


                        }


                        //REMOVED STATE --> OTHER STATE
                        if(a.getTransitions().get(k).getOriginState().equals(stateRemove) &&
                                a.getTransitions().get(k).getDestinyState().equals(a.getTransitions().get(j).getOriginState())){

                            if(!a.getTransitions().get(j).getLabelTransition().equals("#")){
                                a.getTransitions().get(j).setLabelTransition("("+a.getTransitions().get(j).getLabelTransition()+""+
                                        a.getTransitions().get(k).getLabelTransition()+")*");

                                a.getTransitions().get(j).setDestinyState(a.getTransitions().get(k).getDestinyState());

                                a.getTransitions().remove(a.getTransitions().get(k));
                            }

                        }

                        // REMOVED STATE --> REMAIN STATES
                        for (int l = 0; l < a.getTransitions().size(); l++) {


                            if(a.getTransitions().get(l).getOriginState().equals(stateRemove) &&
                                    !a.getTransitions().get(l).getDestinyState().equals(stateRemove)){

                                a.getTransitions().get(j).setLabelTransition(a.getTransitions().get(j).getLabelTransition()+""+
                                        a.getTransitions().get(l).getLabelTransition());
                                a.getTransitions().get(j).setDestinyState(a.getTransitions().get(l).getDestinyState());
                                a.getTransitions().remove(a.getTransitions().get(l));
                            }
                        }

                    }

                }
            }
            a = removeStates(a,stateRemove);
        }


        return a;
    }


    private static Automata removeStates(Automata a, String state){
            for (int i = 0; i < a.getStates().size(); i++) {
                if (a.getStates().get(i).getName().equals(state)){
                    a.getStates().remove(i);
                }
        }

        return a;
    }

    private static Automata removeTransitions(Automata a){
        for (int j = 0; j <a.getEliminationOrder().size() ; j++) {
            for (int i = 0; i < a.getTransitions().size(); i++) {
                if (a.getTransitions().get(i).getOriginState().equals(a.getEliminationOrder().get(j))
                 || a.getTransitions().get(i).getDestinyState().equals(a.getEliminationOrder().get(j))){
                    a.getTransitions().remove(i);
                }
            }
        }
        return a;
    }


    public static void outputSimulate(Automata automata, String fileDot, String graph, String eliminate){
        BuildDotFileAutomata.writeDotFileStep3(automata, BuildDotFileAutomata.pathSaveDot+fileDot+".dot",eliminate);
        ConvertToGraph.readDot(BuildDotFileAutomata.pathSaveDot+fileDot+".dot","AFNtoER_step3_"+graph);
    }

}
