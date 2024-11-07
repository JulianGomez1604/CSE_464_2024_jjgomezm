package org.example;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.ExportException;
import org.jgrapht.nio.ImportException;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;



public class App 
{

    public static void main( String[] args )
    {
        //Creation of Graph for later use
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        //Keyboard input from user
        Scanner scanner = new Scanner(System.in);

        //control variable for command loop
        boolean running = true;


        printCommandOptions();

        while (running) {

            //Prompt user for input and reads the input
            System.out.println("Please select an option:");
            String userInput = scanner.nextLine().toUpperCase();

            switch (userInput) {
                case "A":
                    //prompt for file path and store
                    System.out.println("Please input your file path:\n");
                    userInput = scanner.nextLine();

                    System.out.println(parseGraph(userInput, graph));

                    break;

                case "B":
                    //prompt for input
                    System.out.println("You chose: Add node(s) to the graph.\n " +
                                        "Please enter a comma separated list node1, node2,.... \n");

                    //Save user input
                    userInput = scanner.nextLine();

                    System.out.println(addNode(graph, userInput));

                    break;

                case "C":
                    System.out.println("You chose: Add edge to graph. \n");
                    System.out.println("Please enter the edge in the following order A -> B. (You will prompted for two inputs)\n");

                    System.out.println("Please enter A:");
                    String vertexA = scanner.nextLine();

                    System.out.println("\nPlease enter B:");
                    String vertexB = scanner.nextLine();

                    System.out.println(addEdgeToGraph(vertexA, vertexB, graph));


                    break;

                case "D":
                    System.out.println("You chose: Export graph into image\n");


                    System.out.println("Enter file path to .DOT file: ");
                    userInput = scanner.nextLine();

                    System.out.println(outputGraphics(userInput));

                    break;

                case "E":
                    System.out.println("You chose: Export graph into .DOT file");
                    // Logic for exporting graph into a .DOT file
                    System.out.println(outputDOTGraph(graph));

                    break;

                case "F":
                    printGraphInfo(graph);
                    break;

                case "G":
                    System.out.println("Enter a node to be removed:\n");
                    userInput = scanner.nextLine();

                    System.out.println(removeNode(userInput,graph));

                    break;

                case "H":
                    System.out.println("Enter a list of nodes in a comma separated format:\n"
                                        + "e.g  N1, N2, N3, ...\n");
                    userInput = scanner.nextLine();

                    //Removing all white space
                    String modified = userInput.replaceAll("\\s","");

                    //Parsing input with every appearance of ","
                    String[] items = modified.split(",");

                    System.out.println(removeNodes(items, graph));

                    break;

                case "I":
                    System.out.println("Please enter the edge in the following order A -> B to be removed. (You will prompted for two inputs)\n");

                    System.out.println("Please enter A:");
                    String vA = scanner.nextLine();

                    System.out.println("\nPlease enter B:");
                    String vB = scanner.nextLine();

                    System.out.println(removeEdge(vA, vB, graph));
                    break;

                case "J":
                    System.out.println("Please enter the nodes in which you want to find the path to using BFS: (You will be prompted for two inputs)\n");

                    System.out.println("Please enter the src node:");
                    Node srcNode = new Node(scanner.nextLine());

                    System.out.println("Please enter dst node:");
                    Node dstNode = new Node(scanner.nextLine());

                    System.out.println(GraphSearch(srcNode, dstNode, graph).toString());
                    break;

                case "Q":
                    System.out.println("Exiting program...");
                    running = false; // Set running = false to exit loop
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            if (!userInput.equalsIgnoreCase("q")){
                printCommandOptions();
            }
        }
        scanner.close();
    }

    //Implementing BFS with path return
    public static CustomPath GraphSearch(Node src, Node dst, Graph<String, DefaultEdge> gr) {
        CustomPath path = new CustomPath();

        //Instantiate object for BFS
        BFSShortestPath<String, DefaultEdge> bfsShortestPath = new BFSShortestPath<>(gr);

        //Give me path in form of a list
        List<String> pathToDST = bfsShortestPath.getPath(src.getLabel(), dst.getLabel()).getVertexList();

        for (String item : pathToDST) {
            path.addNode(new Node(item));
        }

        return path;
    }

    //print function for graphs
    public static void printGraphInfo(Graph<String, DefaultEdge> gr) {
        System.out.println(
                "Format: ([vertex1, vertex2,...], [(vertex1, vertex2), (vertex2, vertex1)])  \n " +
                "(vertex1, vertex2) == vertex1 -> vertex2\n");
        System.out.println(gr + "\n" +
                "Number of Nodes: " + gr.vertexSet().size() +"\n------------------------------\n");
    }

    // Logic for importing graph from .DOT file
    public static String parseGraph(String filePath, Graph<String, DefaultEdge> gr) {

        //Create Importer for file
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();

        //Sets how the vertices will be created based on a regex
        importer.setVertexFactory(label -> label);

        //Read file and parse
        try (FileReader fr = new FileReader(filePath)){
            importer.importGraph(gr, fr);
        } catch (ImportException | IOException e) {
            return("Failed to import from file. Please check file path.\n");
        }

        return("Your file has been imported.\n");
    }

    public static String removeNode(String label, Graph<String, DefaultEdge> gr) {
        boolean removed = false;
        String result = "";

        if (label.isEmpty()) {
            return "No input has been given.";
        }

        try {
            removed = gr.removeVertex(label);

            if (removed) {
                result = "Node " + label + " has been successfully removed.\n";
            } else {
                result = "Node " + label + " does not exist in the graph\n";
            }
        } catch (IllegalArgumentException e) {
            result = "Failed to remove node '" + label + "'. Please ensure the node is present in the graph.\n";
        }

        return result;
    }

    public static String removeNodes(String[] label, Graph<String, DefaultEdge> gr) {

        StringBuilder result = new StringBuilder();

        for (String node : label) {
            result.append(removeNode(node, gr));
        }

        return result.toString();
    }

    public static String removeEdge(String srcLabel, String dstLabel, Graph<String, DefaultEdge> gr) {

        if (srcLabel.isEmpty() && dstLabel.isEmpty()) {
            return "Both labels are empty please try again.\n";
        }

        if (srcLabel.isEmpty()) {
            return "Source Label is empty please try again.\n";
        }

        if (dstLabel.isEmpty()) {
            return "Destination Label is empty please try again.\n";
        }

        try {
            DefaultEdge edge = gr.getEdge(srcLabel, dstLabel);
            if (edge != null) {
                gr.removeEdge(edge);
                return "Successfully removed edge between " + srcLabel + " " + " and " + dstLabel + ".\n";
            } else {
                return "Edge between " + srcLabel + " and " + dstLabel + " does not exist.\n";
            }
        } catch (IllegalArgumentException e) {
            return "An error occurred while removing an edge.\n";
        }
    }

    //Logic for exporting graph to file
    public static String outputDOTGraph(Graph<String, DefaultEdge> gr){
        //Exporter for graph
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v);

        //Exporting to file using FileWriter
        try (FileWriter writer = new FileWriter("src/outputs/updatedGraph.dot")) {
            exporter.exportGraph(gr, writer);
            return "Exported file successfully!";
        } catch (ExportException | IOException e) {
            return ("Failed to export to file. Please check file path.");
        }

    }

    //Logic for adding a node
    //NOTE: duplicates are taken care of by library
    public static String addNode(Graph<String, DefaultEdge> gr, String nodes) {
        //Assume format for node list to be comma separated list

        if (nodes.isEmpty()){
            return "No input given.\n";
        }

        //Removing all white space
        String modified = nodes.replaceAll("\\s","");

        //Parsing input with every appearance of ","
        String[] items = modified.split(",");

        for (String node : items) {
            if (node.isEmpty()) {
                break;
            }
            try {
                gr.addVertex(node);

            } catch (Exception e) {
                return "Failed to add node.";
            }
        }

        return("Node(s) have been added.\n");
    }

    public static String outputGraphics(String filePath) {
        //checks if file exists
        Path fP = Paths.get(filePath);

        if (Files.exists(fP) && Files.isRegularFile(fP)) {
            System.out.println("File Found!");

            File dotFile = new File(fP.toString());
            File pngFile = new File("src/outputs/graphics/graph.png");

            try {

                Graphviz.fromFile(dotFile).width(700).render(Format.PNG).toFile(pngFile);
                //Graphviz.fromGraph(graph).width(700).render(Format.PNG).toFile(pngFile);

                return("PNG file generated successfully!");
            } catch (IOException e) {
                return("Something went wrong when exporting to graphics.");
            }

        } else  {
            return("File not Found.");
        }

    }

    public static String addEdgeToGraph(String vA, String vB, Graph<String, DefaultEdge> gr) {

        if (vA.isEmpty() || vB.isEmpty()) {
            return "One of the two inputs is empty please try again.\n";
        }

        if(!gr.containsVertex(vA)) {
            System.out.println("Vertex " + vA + " does not exist and has been added.");
            gr.addVertex(vA);
        }

        if(!gr.containsVertex(vB)) {
            System.out.println("Vertex " + vB + " does not exist and has been added.\n");
            gr.addVertex(vB);
        }

        gr.addEdge(vA,vB);
        return ("Your edge has been added to the graph.\n");
        // Logic for adding an edge to the graph
    }

    //Function to print User Menu
    public static void printCommandOptions() {
                System.out.println("Command Line Options: \n" +
                "\tA: Import graph from .DOT file\n" +
                "\tB: Add node to graph\n" +
                "\tC: Add edge to graph\n" +
                "\tD: Export graph into image\n" +
                "\tE: Export graph into .DOT file\n" +
                "\tF: Print Graph String Format\n" +
                "\tG: Remove Single Node\n" +
                "\tH: Remove Multiple Node\n" +
                "\tI: Remove Edge\n" +
                "\tQ: Quit Program\n\n");
    }
}
