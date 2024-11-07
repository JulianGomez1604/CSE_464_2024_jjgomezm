package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CustomPath {
    private List<Node> nodeList;

    public CustomPath() {
        this.nodeList = new ArrayList<Node>();
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" -> ");
        for (Node node : nodeList) {
            joiner.add(node.getLabel());
        }
        return joiner.toString();
    }
}
