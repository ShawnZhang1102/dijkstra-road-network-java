// Chengxiang Zhang
// czhang88@u.rochester.edu
// CSC 172 - Project 3

import java.util.*;

public class Graph {
    private Map<String, Node> nodes;
    private List<Edge> edges;

    // Constructor for the 'Graph'
    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    // The method writes about adding a new node to the graph
    public void addNode(String id, double latitude, double longitude) {
        if (!nodes.containsKey(id)) {
            nodes.put(id, new Node(id, latitude, longitude));
        } else {
            System.out.println("Node with ID " + id + " already exists.");
        }
    }

    // Check whether the start and end nodes are valid or not
    public void addEdge(String id, String startNode, String endNode) {
        Node start = nodes.get(startNode);
        Node end = nodes.get(endNode);

        if (start == null || end == null) {
            System.out.println("Error adding edge: " + id +
                    " (start=" + startNode + ", end=" + endNode + ")");
            return;
        }

        // Create and add a new edge
        Edge edge = new Edge(id, start, end);
        if (!edges.contains(edge)) {
            edges.add(edge);
            start.addNeighbor(edge);
            end.addNeighbor(edge);
        } else {
            System.out.println("Edge with ID " + id + " already exists.");
        }
    }

    public Map<String, Node> getNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }
}
