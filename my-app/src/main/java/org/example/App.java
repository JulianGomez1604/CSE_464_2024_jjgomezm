package org.example;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import java.util.Scanner;


/**
 * Hello world!
 *
 */
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

        while (running) {

            //print options for user to select from
            printCommandOptions();

            //Prompt user for input and reads the input
            System.out.println("Please select an option:");
            String userInput = scanner.nextLine().toUpperCase();

            switch (userInput) {
                case "A":
                    System.out.println("You chose: Import graph from .DOT file");
                    // Logic for importing graph from .DOT file
                    break;

                case "B":
                    System.out.println("You chose: Add node to graph");
                    // Logic for adding a node to the graph
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
                    break;

                case "Q":
                    System.out.println("Exiting program...");
                    running = false; // Set running = false to exit loop
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        printCommandOptions();

        scanner.close();
    }




    public void importDOT (String filePath) {


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
