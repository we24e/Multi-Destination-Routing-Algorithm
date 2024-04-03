import java.util.*;

public class Dijkstra {
  public static Result shortestPath(Graph graph, Vertex source) {
    Map<Vertex, Integer> distances = new HashMap<>();
    Map<Vertex, Vertex> predecessors = new HashMap<>();
    Set<Vertex> visited = new HashSet<>(); // Visited vertices
    PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
    // Initialize distances
    for (Vertex vertex : graph.getVertices()) {
      distances.put(vertex, Integer.MAX_VALUE);
    }

    distances.put(source, 0);
    queue.add(source);
    while (!queue.isEmpty()) {
      Vertex current = queue.poll();
      visited.add(current);
      for (Edge edge : current.getEdges()) {
        Vertex neighbor = edge.getOtherVertex(current);
        if (neighbor == null || visited.contains(neighbor)) continue; // Skip visited neighbors
        int newDist = distances.get(current) + edge.getWeight();
        if (newDist < distances.get(neighbor)) {
          distances.put(neighbor, newDist);
          predecessors.put(neighbor, current);
          queue.remove(neighbor);
          queue.add(neighbor);
        }
      }
    }
    return new Result(distances, predecessors);
  }
}
