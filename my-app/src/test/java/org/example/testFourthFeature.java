package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.junit.Test;

import static org.example.App.*;
import static org.junit.Assert.assertEquals;

public class testFourthFeature {

    @Test
    public void checkOutputDOTGraph() {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        importGraph("src/dot files/graph1.dot", graph);

        assertEquals("Exported file successfully!", fileOutputDOTGraph(graph));
    }


    @Test
    public void checkoutputGraphicFilePresent() {
        assertEquals("PNG file generated successfully!", outputGraphics("src/dot files/graph1.dot"));
    }
}
