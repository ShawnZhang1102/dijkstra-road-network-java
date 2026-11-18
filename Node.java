// Chengxiang Zhang
// czhang88@u.rochester.edu
// CSC 172 - Project 3

import java.util.*;

public class Node {
    private String id;
    private double latitude;
    private double longitude;
    private List<Edge> neighbors;

    // Contructor for the node
    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighbors = new ArrayList<>();
    }
    
    // All methods are get xxx until getNeighbors
    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }

    // Add an edge to the list of neighbors and link it
    public void addNeighbor(Edge edge) {
        neighbors.add(edge);
    }
}