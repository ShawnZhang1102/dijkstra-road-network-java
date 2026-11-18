// Chengxiang Zhang
// czhang88@u.rochester.edu
// CSC 172 - Project 3

public class Edge {
    private final String id;
    private final Node startNode;
    private final Node endNode;

    // Contructor for the Edge
    public Edge(String id, Node startNode, Node endNode) {
        this.id = id;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    // Getter for the 'id'
    public String getId() {
        return id;
    }

    // Getter for the 'startNode'
    public Node getStartNode() {
        return startNode;
    }

    // Getter for the 'endNode'
    public Node getEndNode() {
        return endNode;
    }

    // Get the opposite Node of the given one in the edge, and the method includes a error message if there is no opposite one
    public Node getOppositeNode(Node node) {
        if (node.equals(startNode)) {
            return endNode;
        } else if (node.equals(endNode)) {
            return startNode;
        } else {
            throw new IllegalArgumentException("The provided node is not part of this edge.");
        }
    }

    // Convert the latitude and the longtitude from degrees to radians
    public double calculateDistance() {
        double startLatitude = Math.toRadians(startNode.getLatitude());
        double startLongitude = Math.toRadians(startNode.getLongitude());
        double endLatitude = Math.toRadians(endNode.getLatitude());
        double endLongitude = Math.toRadians(endNode.getLongitude());

        // Haversine formula
        double deltaLat = endLatitude - startLatitude;
        double deltaLon = endLongitude - startLongitude;
        double a = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(startLatitude) * Math.cos(endLatitude)
                * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculation witht eh earth's radius
        final double EARTH_RADIUS_MILES = 3958.8;
        return EARTH_RADIUS_MILES * c;
    }
}
