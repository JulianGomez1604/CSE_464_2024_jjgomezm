package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.Test;

import static org.example.App.addEdgeToGraph;
import static org.junit.Assert.assertEquals;

public class testThirdFeature {

    @Test
    public void checkAddEdgeToGraph() {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        assertEquals("Your edge has been added to the graph.\n",
                addEdgeToGraph("hello", "bye", graph));
    }

    @Test
    public void checkAddEdgeToGraphNoInput() {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        assertEquals("One of the two inputs is empty please try again.\n",
                addEdgeToGraph("", "bye", graph));
    }
}
