import java.util.*;
public class Bellman {

  public static Result shortestPath(Graph graph, Vertex source) {
    Map<Vertex, Integer> distances = new HashMap<>();
    Map<Vertex, Vertex> predecessors = new HashMap<>();

    for (Vertex vertex : graph.getVertices()) {
      distances.put(vertex, Integer.MAX_VALUE);
      predecessors.put(vertex, null);
    }
    distances.put(source, 0);
    for (int i = 0; i < graph.getVertices().size() - 1; i++) {
      for (Edge edge : graph.getEdges()) {
        Vertex u = edge.getVertex1();
        Vertex v = edge.getVertex2();
        int weight = edge.getWeight();
        if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
          distances.put(v, distances.get(u) + weight);
          predecessors.put(v, u);
        }
        if (distances.get(v) != Integer.MAX_VALUE && distances.get(v) + weight < distances.get(u)) {
          distances.put(u, distances.get(v) + weight);
          predecessors.put(u, v);
        }
      }
    }

    for (Edge edge : graph.getEdges()) {
      Vertex u = edge.getVertex1();
      Vertex v = edge.getVertex2();
      int weight = edge.getWeight();
      if (distances.get(u) != Integer.MAX_VALUE && distances.get(u) + weight < distances.get(v)) {
        System.out.println("Graph contains a negative weight cycle");
        return null;
      }
      if (distances.get(v) != Integer.MAX_VALUE && distances.get(v) + weight < distances.get(u)) {
        System.out.println("Graph contains a negative weight cycle");
        return null;
      }
    }
    return new Result(distances, predecessors);
  }
}
