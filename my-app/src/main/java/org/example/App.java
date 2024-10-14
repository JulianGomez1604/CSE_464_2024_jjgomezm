package org.example;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.ExportException;
import org.jgrapht.nio.ImportException;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.dot.DOTImporter;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

                    parseGraph(userInput, graph);

                    //prompt for printing graph in either string format or into updated dot file
                    System.out.println("Your graph has been output as a string.\n" +
                                        "Format: ([vertex1, vertex2,...], [(vertex1, vertex2), (vertex2, vertex1)])  \n " +
                                        "(vertex1, vertex2) == vertex1 -> vertex2\n");
                    System.out.println(graph + "\n" +
                            "Number of Nodes: " + graph.vertexSet().size() +"\n------------------------------");

                    break;

                case "B":
                    //prompt for input
                    System.out.println("You chose: Add node(s) to the graph.\n " +
                                        "Please enter a comma separated list node1, node2,.... \n");

                    //Save user input
                    userInput = scanner.nextLine();

                    addNode(graph, userInput);
                    break;

                case "C":
                    System.out.println("You chose: Add edge to graph");
                    // Logic for adding an edge to the graph
                    break;

                case "D":
                    System.out.println("You chose: Export graph into image");
                    // Logic for exporting graph into an image
                    break;

                case "E":
                    System.out.println("You chose: Export graph into .DOT file");
                    // Logic for exporting graph into a .DOT file
                    outputGraph(graph);
                    System.out.println("Your graph has been exported into a .DOT file.\n------------------------------");
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


    // Logic for importing graph from .DOT file
    public static void parseGraph(String filePath, Graph<String, DefaultEdge> gr) {

        //Create Importer for file
        DOTImporter<String, DefaultEdge> importer = new DOTImporter<>();

        //Sets how the vertices will be created based on a regex
        importer.setVertexFactory(label -> label);

        //Read file and parse
        try (FileReader fr = new FileReader(filePath)){
            importer.importGraph(gr, fr);
        } catch (ImportException | IOException e) {
            System.out.println("Failed to import from file. Please check file path.\n");
        }

        System.out.println("Your file has been imported.\n");
    }

    //Logic for exporting graph to file
    public static void outputGraph(Graph<String, DefaultEdge> gr){
        //Exporter for graph
        DOTExporter<String, DefaultEdge> exporter = new DOTExporter<>(v -> v);

        //Exporting to file using FileWriter
        try (FileWriter writer = new FileWriter("src/outputs/updatedGraph.dot")) {
            exporter.exportGraph(gr, writer);
        } catch (ExportException | IOException e) {
            System.out.println("Failed to export to file. Please check file path.");
        }

    }

    //Logic for adding a node
    //NOTE: duplicates are taken care of by library
    public static void addNode(Graph<String, DefaultEdge> gr, String nodes) {
        //Assume format for node list to be comma separated list

        //Removing all white space
        String modified = nodes.replaceAll("\\s","");

        //Parsing input with delimter of ","
        String[] items = modified.split(",");

        for (String node : items) {
            if (node.isEmpty()) {
                break;
            }
            gr.addVertex(node);
        }

        System.out.println("Node(s) have been added.\n");
    }

    //Function to print User Menu
    public static void printCommandOptions() {
                System.out.println("Command Line Options: \n" +
                "\tA: Import graph from .DOT file\n" +
                "\tB: Add node to graph\n" +
                "\tC: Add edge to graph\n" +
                "\tD: Export graph into image\n" +
                "\tE: Export graph into .DOT file\n" +
                "\tQ: Quit Program\n\n");
    }
}
