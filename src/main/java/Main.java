import controller.AFNtoER;
import controller.BuildAutomata;
import controller.BuildDotFileAutomata;
import graphviz.ConvertToGraph;
import model.Automata;
import model.State;

import java.util.Scanner;


public class Main {


    private static Automata automata = new Automata();


    public static void main(String[] args) {
        apresentation();
        initialAutomata();
        convertAFNtoER();

    }

    private static void apresentation(){
        System.out.println("IFMG - LFA FINAL WORK");
        System.out.println("\n");
        System.out.println("PROGRAM TO CONVERT AFN-λ TO REGULAR EXPRESSION");
        System.out.println("\n");
        System.out.println("GROUP:  PATRÍCIA PIERONI AMARANTE 0011334");
        System.out.println("\t\tVINÍCIUS MORAIS ");
        System.out.println("\t\tNARA CARVALHO NASCIMENTO ");
        System.out.println("\n");
        System.out.print("TYPE THE FILE NAME: ");
        Scanner scanner = new Scanner( System.in );
        automata = BuildAutomata.readAndBuild(scanner.nextLine());
        System.out.println(automata);
    }

    private static void initialAutomata(){
        BuildDotFileAutomata.writeDotFile(automata,BuildDotFileAutomata.pathSaveDot+"af.dot",0);
        ConvertToGraph.readDot(BuildDotFileAutomata.pathSaveDot+"af.dot","initialAutomata");
    }

    /**
     * Algorithm to convert AFN-λ to ER
     */
    public static void convertAFNtoER(){



        //STEP 1
        Automata convertedAFN = AFNtoER.addingNewStates(automata);
        BuildDotFileAutomata.writeDotFile(convertedAFN, BuildDotFileAutomata.pathSaveDot+"step1.dot",1);
        ConvertToGraph.readDot(BuildDotFileAutomata.pathSaveDot+"step1.dot","AFNtoER_step1");

        System.out.println(convertedAFN);

        //STEP 2
        Automata convertedAFN2 = AFNtoER.transitionsSubstitutions(convertedAFN);

        System.out.println(convertedAFN2);
        BuildDotFileAutomata.writeDotFile(convertedAFN2, BuildDotFileAutomata.pathSaveDot+"step2.dot",2);
        ConvertToGraph.readDot(BuildDotFileAutomata.pathSaveDot+"step2.dot","AFNtoER_step2");

        System.out.println();
        System.out.println("STEP 3");
        System.out.println(AFNtoER.convertToER(convertedAFN2).toString());


        AFNtoER.stateElimination(convertedAFN2);


    }



}
