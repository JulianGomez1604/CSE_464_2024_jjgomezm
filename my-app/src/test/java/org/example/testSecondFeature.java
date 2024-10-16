package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.jupiter.api.Test;

import static org.example.App.addNode;
import static org.junit.Assert.assertEquals;


public class testSecondFeature {

    @Test
    public void checkAddNodesCorrect () {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);


        assertEquals("Node(s) have been added.\n",addNode(graph,"A, B,    C,   D   , J"));
    }

    @Test
    public void checkAddNodeEmptyInput () {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        assertEquals("No input given.\n", addNode(graph,""));

    }


}
