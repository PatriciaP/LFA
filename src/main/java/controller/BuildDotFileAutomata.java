package controller;

import graphviz.ConvertToGraph;
import model.Automata;
import model.State;
import model.Transition;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BuildDotFileAutomata {

    private static FileWriter fileWriter = null;

    public static String pathSaveDot = "C:/Users/patri/.IntelliJIdea2018.1/config/scratches/";


    public static void writeDotFile(Automata automata, String name, int step) {

        try {
            fileWriter = new FileWriter(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fileWriter != null;

        PrintWriter printWriter= new PrintWriter(fileWriter);

        settingStyleConfig(automata, printWriter,step);

        //creating the -> for the initials states
        for (int i = 0; i < automata.getStates().size(); i++) {
            if (automata.getStates().get(i).isInitialState()) {
                printWriter.println("x" + i + " -> " + automata.getStates().get(i).getName() + ";");
            }
        }

        if(step < 2){
            treatingNullTransitions(automata, printWriter, step);
            treatingTransitions(automata, printWriter, step);
        } else {
            treatingTransitions(automata, printWriter, step);
        }
        printWriter.println("}");
        printWriter.close();

    }


    public static void writeDotFileStep3(Automata automata, String name, String stateEliminate) {

        try {
            fileWriter = new FileWriter(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fileWriter != null;

        PrintWriter printWriter= new PrintWriter(fileWriter);

        settingStyleConfig(automata, printWriter,3);

        //creating the -> for the initials states
        for (int i = 0; i < automata.getStates().size(); i++) {
            if (automata.getStates().get(i).isInitialState()) {
                printWriter.println("x" + i + " -> " + automata.getStates().get(i).getName() + ";");
            }
        }

        coloringOrder(automata,printWriter,stateEliminate);

        printWriter.println("}");
        printWriter.close();

    }

    private static void settingStyleConfig(Automata automata, PrintWriter printWriter, int step) {
        printWriter.println("digraph af {");
        //DIRECTION LETF -> RIGHT
        printWriter.println("rankdir=LR;");
        printWriter.println("size=\"8,5\"");

        printWriter.println("node [style = filled,  fillcolor=gray84]");

        if(step==1){
            //first step of the conversion
            coloringInitialAndFinal(automata, printWriter);
        }

        //create new nodes to point for the initial state
        for (int i = 0; i < automata.getStates().size(); i++) {
            if (automata.getStates().get(i).isInitialState()) {
                printWriter.println("x" + i + " [shape = point] ");
            }

        }

        //defining the shape of the finals states
        for (State state : automata.getStates()) {
            if (state.isFinalState())
                printWriter.println(state.getName() + " [shape = doublecircle] ");
        }

        //defining shape of all nodes
        printWriter.println("node [shape = circle];");

    }


    /**
     * Setting the color of the new Initial and
     * Final State of the converted afn
     */
    private static void coloringInitialAndFinal(Automata automata, PrintWriter printWriter) {

        for (State state : automata.getStates()) {
            if (state.isInitialState() && state.getName().equals("i")) {
                printWriter.println("i [color=Black, style=filled, fillcolor=Orange]");
            }
            if (state.isFinalState() && state.getName().equals("f")) {
                printWriter.println("f [color=Black, style=filled, fillcolor=Orange]");
            }
        }

    }

    /**
     * Treating the λ transition
     * and coloring the transitions when start the conversion
     */
    private static void treatingNullTransitions(Automata automata, PrintWriter printWriter, int step){

        for (Transition t : automata.getTransitions()) {

            if(step == 0){
                if (t.getLabelTransition().equals("#"))
                    t.setLabelTransition("λ");
            }
            if (t.getLabelTransition().equals("λ")) {
                //coloring the new transitions from the first step of the conversion
                if (t.getOriginState().equals("i")) {
                    printWriter.println(t.getOriginState() + " -> " + t.getDestinyState() +
                            "[label = \""+t.getLabelTransition()+"\", color=Orange, fontcolor=Orange];");
                } else if (t.getDestinyState().equals("f")) {
                    printWriter.println(t.getOriginState() + " -> " + t.getDestinyState() +
                            "[label = \""+t.getLabelTransition()+"\", color=Orange, fontcolor=Orange];");
                } else
                    printWriter.println(t.getOriginState() + " -> " + t.getDestinyState() +
                            "[label = \""+t.getLabelTransition()+"\"];");
            }
        }

    }

    private static void treatingTransitions(Automata automata, PrintWriter printWriter, int step){

        if(step == 2){

            for (int i = 0; i < automata.getTransitions().size(); i++) {
                String origin = automata.getTransitions().get(i).getOriginState();
                String destiny = automata.getTransitions().get(i).getDestinyState();

                if(automata.getTransitions().get(i).getLabelTransition().toString().matches(".+.")){
                    printWriter.println(origin + " -> " + destiny +
                            "[label = \"" + automata.getTransitions().get(i).getLabelTransition()  + "\" color=brown, fontcolor=brown];");
                } else
                printWriter.println(origin + " -> " + destiny +
                        "[label = \"" + automata.getTransitions().get(i).getLabelTransition()  + "\"];");
            }

        } else
            // creating the others transitions
            for (int i = 0; i < automata.getTransitions().size(); i++) {
                String origin = automata.getTransitions().get(i).getOriginState();
                String destiny = automata.getTransitions().get(i).getDestinyState();

                if (!automata.getTransitions().get(i).getLabelTransition().equals("λ")) {
                    //treating when have more than one transition for the same destiny
                    for (int j = i + 1; j < automata.getTransitions().size(); j++) {
                        if (automata.getTransitions().get(j).getOriginState().equals(origin)) {
                            if (automata.getTransitions().get(j).getDestinyState().equals(destiny)) {
                                printWriter.println(origin + " -> " + destiny +
                                        "[label = \"" + automata.getTransitions().get(i).getLabelTransition() + "," +
                                        automata.getTransitions().get(j).getLabelTransition() + "\"];");
                            } else {
                                printWriter.println(origin + " -> " + destiny +
                                        "[label = \"" + automata.getTransitions().get(i).getLabelTransition() + "\"];");
                                break;
                            }
                        }
                    }
                }

            }
    }



    private static void coloringOrder(Automata afn, PrintWriter printWriter, String stateEliminate){

        String color = hexadecimalColor(randomColor());
        printWriter.println(stateEliminate+" [color=Black, style=filled, fillcolor=\""+color+"\"]");

        for (Transition t: afn.getTransitions()) {

            if(t.getOriginState().equals(stateEliminate) ||
                    t.getDestinyState().equals(stateEliminate)){
                printWriter.println(t.getOriginState() + " -> " + t.getDestinyState() +
                        "[label = \"" + t.getLabelTransition()  + "\", color=\""+color+"\", fontcolor=\""+color+"\"];");
            } else {
                printWriter.println(t.getOriginState() + " -> " + t.getDestinyState() +
                        "[label = \"" + t.getLabelTransition()  + "\"];");
            }

        }



    }


    /**
     * Generate random color
     * @return
     */
    private static Color randomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private static String hexadecimalColor(Color color){
        return '#'+
                treatingHexString(Integer.toHexString(color.getRed()))+
                treatingHexString(Integer.toHexString(color.getGreen()))+
                treatingHexString(Integer.toHexString(color.getBlue()));
    }

    private static String treatingHexString(String hexString){
            String hex = null;
           if(hexString.length() == 1){
                       hex = '0'+hexString;
                 }else{
                       hex = hexString;
                 }
               return hex;
    }


}
