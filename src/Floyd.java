import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Floyd {
  public static Map<Vertex, Map<Vertex, Result>> shortestPath(Graph graph) {
    int n = graph.getVertices().size();
    double[][] dist = new double[n][n];
    Vertex[][] next = new Vertex[n][n];
    Map<Integer, Vertex> indexToVertex = new HashMap<>();
    Map<Vertex, Integer> vertexToIndex = new HashMap<>();
    int index = 0;
    for (Vertex v : graph.getVertices()) {
      Arrays.fill(dist[index], Double.POSITIVE_INFINITY);
      dist[index][index] = 0;
      indexToVertex.put(index, v);
      vertexToIndex.put(v, index++);
    }

    for (Edge e : graph.getEdges()) {
      int u = vertexToIndex.get(e.getVertex1()), v = vertexToIndex.get(e.getVertex2());
      dist[u][v] = e.getWeight();
      next[u][v] = e.getVertex2();
    }

    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (dist[i][k] + dist[k][j] < dist[i][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }
    Map<Vertex, Map<Vertex, Result>> results = new HashMap<>();
    for (int i = 0; i < n; i++) {
      Vertex source = indexToVertex.get(i);
      Map<Vertex, Result> shortestRes = new HashMap<>();
      for (int j = 0; j < n; j++) {
        Vertex destination = indexToVertex.get(j);
        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        if (next[i][j] != null) { // If there is a path
          distances.put(destination, (int) dist[i][j]);
          Vertex at = source;
          while (at != destination) {
            Vertex nextVertex = next[vertexToIndex.get(at)][j];
            if (nextVertex == null) break; // Path interrupted
            predecessors.put(nextVertex, at);
            at = nextVertex;
          }
        }
        shortestRes.put(destination, new Result(distances, predecessors));
      }
      results.put(source, shortestRes);
    }

// debug only!!!!
//    for (int i = 0; i < n; i++) {
//      for (int j = 0; j < n; j++) {
//        if (i != j && next[i][j] != null) {
//          System.out.print("Path from " + i + " to " + j + ": ");
//          int u = i;
//          while (u != j) {
//            System.out.print(u + " -> ");
//            if (next[u][j] == null) break; // Prevent infinite loop
//            u = vertexToIndex.get(next[u][j]);
//          }
//          System.out.println(j);
//        }
//      }
//    }

    return results;
  }
}
