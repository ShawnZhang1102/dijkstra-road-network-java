// Chengxiang Zhang
// czhang88@u.rochester.edu
// CSC 172 - Project 3

import java.util.*;

public class Dijkstra {
    public static List<Node> findShortestPath(Graph graph, String startId, String endId) {
        
        // Initialization of the Map and PriorityQueue
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        Node startNode = graph.getNodes().get(startId);
        Node endNode = graph.getNodes().get(endId);

        // This determins whether the startNode and the endNode are validate or not
        if (startNode == null || endNode == null) {
            System.out.println("Error: Start or end node not found.");
            return Collections.emptyList();
        }

        // Initializatino of distances and queus
        graph.getNodes().values().forEach(node -> distances.put(node, Double.MAX_VALUE));
        distances.put(startNode, 0.0);
        queue.add(startNode);

        // Poll the shortest distance from the queue
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode.equals(endNode)) break;

            // Iterate the neighbors
            for (Edge edge : currentNode.getNeighbors()) {
                Node neighbor = edge.getOppositeNode(currentNode);
                double newDist = distances.get(currentNode) + edge.calculateDistance();

                // Update the shortest distances
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, currentNode);

                    if (queue.contains(neighbor)) queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        // Build the path
        List<Node> path = buildPath(previous, startNode, endNode);
        if (path.isEmpty() || !path.get(0).equals(startNode)) {
            System.out.println("No path found.");
            return Collections.emptyList();
        }

        return path;
    }

    // Backtrack from the endNode to the startNode with previous
    private static List<Node> buildPath(Map<Node, Node> previous, Node startNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        for (Node current = endNode; current != null; current = previous.get(current)) {
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}
