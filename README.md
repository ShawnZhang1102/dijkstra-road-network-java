# Shortest Path on Road Networks (Dijkstra's Algorithm)

> Class Project · CSC 172, University of Rochester

Finds the shortest driving route between two points on a real road network, using Dijkstra's algorithm — with a graphical map that draws the route.

## What It Does

- **Graph construction**: reads road-network data files (nodes with latitude/longitude, roads as edges) and builds a graph.
- **Dijkstra's algorithm**: computes the shortest path between a start and an end point.
- **Visualization**: a Swing GUI renders the road network and highlights the shortest path in blue.

## Tech Stack

Java · Swing · Dijkstra's algorithm · Graph data structures

## How to Run

```bash
javac *.java
java Main
```

Pick a start and an end point in the GUI to see the shortest path drawn on the map.

## Files

| File | What it is |
| --- | --- |
| `Main.java` | Entry point and GUI |
| `Graph.java` | Graph structure |
| `Node.java` | Graph node (intersection, with coordinates) |
| `Edge.java` | Graph edge (road) |
| `Dijkstra.java` | Dijkstra's shortest-path algorithm |
| `nys.txt` | New York State road network data (35 MB) |
| `monroe.txt` | Monroe County road network data |
| `ur.txt` | University of Rochester campus road data |

## Data Note

The program reads its map data from the `.txt` files above. `nys.txt` is the full New York State road network, which is why the repository is larger than a typical code project.
