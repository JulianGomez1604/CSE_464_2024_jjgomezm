package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.Before;
import org.junit.Test;

import static org.example.App.parseGraph;
import static org.junit.Assert.assertEquals;

public class testFirstFeature {

    @Test
    public void checkDOTParseCorrect () {

        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        assertEquals("Your file has been imported.\n", parseGraph("src/dot files/graph2.dot", graph));
    }

    @Test
    public void checkDOTParseFileNotFound (){
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        assertEquals("Failed to import from file. Please check file path.\n", parseGraph("src/dot files/graph3.dot", graph));
    }

}
