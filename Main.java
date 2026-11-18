// Chengxiang Zhang
// czhang88@u.rochester.edu
// CSC 172 - Project 3

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Main extends JPanel {
    private final Graph graph;
    private List<Node> pathNodes;
    private boolean showPath = false;

    public Main(String fileName) throws IOException {
        graph = new Graph();
        load(fileName);
    }

    // Read the input and add the i and r into the graph
    private void load(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts[0].equals("i")) {
                    graph.addNode(parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                } else if (parts[0].equals("r")) {
                    graph.addEdge(parts[1], parts[2], parts[3]);
                }
            }
        }
    }

    // Store the shortest path
    public void setPath(List<Node> path) {
        this.pathNodes = new ArrayList<>(path);
        this.showPath = true;
        repaint();
    }

    // Override and render a white background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        double[] bounds = calculateBounds();
        double minLat = bounds[0], maxLat = bounds[1], minLon = bounds[2], maxLon = bounds[3];

        // Iterate the edges and draw the line in LIGHT_GRAY
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1));
        for (Edge edge : graph.getEdges()) {
            drawEdge(g2d, edge, minLat, maxLat, minLon, maxLon);
        }

        // Draw the shortest path in blue
        if (showPath && pathNodes != null && pathNodes.size() > 1) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3));
            for (int i = 0; i < pathNodes.size() - 1; i++) {
                Node current = pathNodes.get(i);
                Node next = pathNodes.get(i + 1);
                drawPathSegment(g2d, current, next, minLat, maxLat, minLon, maxLon);
            }
        }

        // Draw the nodes in green
        g2d.setColor(Color.GREEN);
        for (Node node : graph.getNodes().values()) {
            drawNode(g2d, node, minLat, maxLat, minLon, maxLon);
        }

        drawLegend(g2d);
    }

    // Calculate te bounds
    private double[] calculateBounds() {
        double minLat = Double.MAX_VALUE;
        double maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = -Double.MAX_VALUE;

        for (Node node : graph.getNodes().values()) {
            minLat = Math.min(minLat, node.getLatitude());
            maxLat = Math.max(maxLat, node.getLatitude());
            minLon = Math.min(minLon, node.getLongitude());
            maxLon = Math.max(maxLon, node.getLongitude());
        }

        return new double[]{minLat, maxLat, minLon, maxLon};
    }

    private void drawEdge(Graphics2D g2d, Edge edge, double minLat, double maxLat, double minLon, double maxLon) {
        Node start = edge.getStartNode();
        Node end = edge.getEndNode();

        int x1 = transformX(start.getLongitude(), minLon, maxLon);
        int y1 = transformY(start.getLatitude(), minLat, maxLat);
        int x2 = transformX(end.getLongitude(), minLon, maxLon);
        int y2 = transformY(end.getLatitude(), minLat, maxLat);

        g2d.drawLine(x1, y1, x2, y2);
    }

    private void drawPathSegment(Graphics2D g2d, Node start, Node end, double minLat, double maxLat, double minLon, double maxLon) {
        int x1 = transformX(start.getLongitude(), minLon, maxLon);
        int y1 = transformY(start.getLatitude(), minLat, maxLat);
        int x2 = transformX(end.getLongitude(), minLon, maxLon);
        int y2 = transformY(end.getLatitude(), minLat, maxLat);

        g2d.drawLine(x1, y1, x2, y2);
    }

    private void drawNode(Graphics2D g2d, Node node, double minLat, double maxLat, double minLon, double maxLon) {
        int x = transformX(node.getLongitude(), minLon, maxLon);
        int y = transformY(node.getLatitude(), minLat, maxLat);
        g2d.fillOval(x - 3, y - 3, 6, 6);
    }

    private int transformX(double lon, double minLon, double maxLon) {
        return (int) ((lon - minLon) / (maxLon - minLon) * (getWidth() - 40) + 20);
    }

    private int transformY(double lat, double minLat, double maxLat) {
        return (int) ((maxLat - lat) / (maxLat - minLat) * (getHeight() - 40) + 20);
    }

    private void drawLegend(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        int y = 20;

        g2d.setColor(Color.RED);
        g2d.fillOval(10, y, 6, 6);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Intersection", 20, y + 6);

        y += 20;
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(10, y, 18, y);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Road", 20, y + 4);

        if (showPath) {
            y += 20;
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(10, y, 18, y);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Shortest Path", 20, y + 4);
        }
    }

    private void printPath(List<Node> path) {
        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        double totalDistance = 0;
        System.out.println("\nPath from " + path.get(0).getId() + " to " +
                path.get(path.size() - 1).getId() + ":");

        for (int i = 0; i < path.size() - 1; i++) {
            Node current = path.get(i);
            Node next = path.get(i + 1);
            Edge edge = findEdge(current, next);
            if (edge != null) {
                totalDistance += edge.calculateDistance();
                System.out.println(current.getId() + " -> " + next.getId());
            }
        }

        System.out.printf("Total distance: %.2f miles%n", totalDistance);
    }

    private Edge findEdge(Node start, Node end) {
        for (Edge edge : start.getNeighbors()) {
            if ((edge.getStartNode() == start && edge.getEndNode() == end) ||
                    (edge.getStartNode() == end && edge.getEndNode() == start)) {
                return edge;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main <filename> [--show] [--directions startNode endNode]");
            return;
        }

        String fileName = args[0];
        boolean showMap = false;
        String startNode = null, endNode = null;

        for (int i = 1; i < args.length; i++) {
            if ("--show".equals(args[i])) {
                showMap = true;
            } else if ("--directions".equals(args[i]) && i + 2 < args.length) {
                startNode = args[i + 1];
                endNode = args[i + 2];
                i += 2;
            }
        }

        try {
            Main panel = new Main(fileName);

            if (startNode != null && endNode != null) {
                List<Node> path = Dijkstra.findShortestPath(panel.graph, startNode, endNode);
                panel.printPath(path);
                if (showMap) {
                    panel.setPath(path);
                }
            }

            if (showMap) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Street Map");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(panel);
                    frame.setSize(800, 800);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}