package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.Test;

import static org.example.App.*;
import static org.junit.Assert.assertEquals;

public class testPart2 {

    //Scenario 1 Testing
    @Test
    public void checkScenario1 () {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        //adding nodes and edges for testing later
        graph.addVertex("a");
        graph.addVertex("b");
        graph.addVertex("c");
        graph.addVertex("d");
        graph.addVertex("e");
        graph.addVertex("f");

        graph.addEdge("a","d");
        graph.addEdge("b","d"); //this
        graph.addEdge("c","e");
        graph.addEdge("f","d");
        graph.addEdge("c","b");
        graph.addEdge("b","f");

        String[] input = {"e","f"};
        StringBuilder strResult = new StringBuilder();
        strResult.append("Node e has been successfully removed.\n");
        strResult.append("Node f has been successfully removed.\n");

        //testing starts

        //removing node using both functions removeNode and removeNodes
        assertEquals("Node a has been successfully removed.\n", removeNode("a", graph));
        assertEquals(strResult.toString(), removeNodes(input, graph));

        //removing edges
        assertEquals("Successfully removed edge between b  and d.\n", removeEdge("b", "d", graph));
        assertEquals("Successfully removed edge between c  and b.\n", removeEdge("c", "b", graph));
    }

    //Scenario 2 Testing
    @Test
    public void checkScenario2 () {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        //node 'a' was never added to the graph

        assertEquals("Node a does not exist in the graph\n", removeNode("a", graph));
    }

    //Scenario 3 Testing
    @Test
    public void checkScenario3() {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        //edge a -> b was never added to the graph

        assertEquals("Edge between a and b does not exist.\n", removeEdge("a", "b", graph));
    }
}
