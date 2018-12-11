package graphviz;

import java.io.File;

public class ConvertToGraph {

    /**
     * Read the DOT source from a file,
     * convert to image and store the image in the file system.
     */
    public static void readDot(String input, String name )
    {

        GraphViz gv = new GraphViz();
        gv.readSource(input);
        System.out.println(gv.getDotSource());

        //String type = "gif";
        //String type = "dot";
        //String type = "fig";    // open with xfig
        //String type = "pdf";
        //String type = "ps";
        //String type = "svg";    // open with inkscape
        String type = "png";
        //String type = "plain";

        String repesentationType= "dot";
        //		String repesentationType= "neato";
        //		String repesentationType= "fdp";
        //		String repesentationType= "sfdp";
        // 		String repesentationType= "twopi";
        //		String repesentationType= "circo";

        String path ="C:\\Users\\patri\\Documents\\IFMG\\6_Periodo\\LFA\\src\\main\\resources\\output\\";
        //File out = new File("/tmp/simple." + type);   // Linux
        File out = new File(path+name+"."+ type);   // Windows
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
    }


    /**
     * Construct a DOT graph in memory, convert it
     * to image and store the image in the file system.
     */
    public static void constructDot()
    {
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        gv.addln("A -> B;");
        gv.addln("A -> C;");
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());

        gv.increaseDpi();   // 106 dpi

        String type = "gif";
        //      String type = "dot";
        //      String type = "fig";    // open with xfig
        //      String type = "pdf";
        //      String type = "ps";
        //      String type = "svg";    // open with inkscape
        //      String type = "png";
        //      String type = "plain";

        String repesentationType= "dot";
        //		String repesentationType= "neato";
        //		String repesentationType= "fdp";
        //		String repesentationType= "sfdp";
        // 		String repesentationType= "twopi";
        // 		String repesentationType= "circo";

        File out = new File("/tmp/out"+gv.getImageDpi()+"."+ type);   // Linux
        //      File out = new File("c:/eclipse.ws/graphviz-java-api/out." + type);    // Windows
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
    }

}
