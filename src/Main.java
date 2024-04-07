import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {

    // wxy: test here!!!!

    // ----------------- Test 1 -----------------
    GraphConstructor constructor = new GraphConstructor();
//    constructor.constructSampleGraph();
    constructor.GraphApp(50, 3, 7);
    Graph graph = constructor.getGraph();
    List<Vertex> destinations = constructor.getDestinations();
    // -------------- End of Test 1 --------------
    
    /**
     * Wxy: Please add other tests here, thank you!
     */
    
    long startTime, endTime, duration;
    
    Vertex start = destinations.get(0); // 1st vertex is the start

    //Dijkstra
    startTime = System.currentTimeMillis();
    Map<Vertex, Result> DijkstraRes = new HashMap<>();
    for (Vertex destination : destinations) {
      DijkstraRes.put(destination, Dijkstra.shortestPath(graph, destination));
    }
    findShorest(destinations, DijkstraRes, "Dijkstra");
    endTime = System.currentTimeMillis();
    duration = endTime - startTime;
    System.out.println("\nDijkstra's algorithm runtime: " + duration + " ms\n");

    //Bellman-Ford
    startTime = System.currentTimeMillis();
    Map<Vertex, Result> BellmanRes = new HashMap<>();
    for (Vertex destination : destinations) {
      BellmanRes.put(destination, Bellman.shortestPath(graph, destination));
    }
    findShorest(destinations, BellmanRes, "Bellman-Ford");
    endTime = System.currentTimeMillis();
    duration = endTime - startTime;
    System.out.println("\nBellman-Ford algorithm runtime: " + duration + " ms\n");

    // Floyd-Warshall
    startTime = System.currentTimeMillis();
    Map<Vertex, Map<Vertex, Result>> FloydRes = Floyd.shortestPath(graph);
    Warshall(destinations, FloydRes);
    endTime = System.currentTimeMillis();
    duration = endTime - startTime;
    System.out.println("\nFloyd-Warshall algorithm runtime: " + duration + " ms");
  }

  private static void Warshall(List<Vertex> destinations, Map<Vertex, Map<Vertex, Result>> pairShortest) {
    Vertex start = destinations.get(0); // Start vertex
    List<Vertex> destinationsPerm = new ArrayList<>(destinations);
    destinationsPerm.remove(start); // Remove start
    int shortestDistance = Integer.MAX_VALUE;
    List<Vertex> shortestPath = new ArrayList<>();
    // all permutations
    List<List<Vertex>> permutations = generatePermutations(destinationsPerm);

    for (List<Vertex> permutation : permutations) {
      permutation.add(0, start); // Add start
      int totalDistance = 0;
      List<Vertex> currentPath = new ArrayList<>();
      boolean validPath = true;

      for (int i = 0; i < permutation.size() - 1; i++) {
        Vertex current = permutation.get(i);
        Vertex next = permutation.get(i + 1);
        Result result = pairShortest.get(current).get(next);

        if (result == null || result.getDistances().get(next) == null) {
          validPath = false;
          break; // No valid path
        }

        int distance = result.getDistances().get(next);
        List<Vertex> pathSegment = result.getPath(next);

        if (!pathSegment.isEmpty()) {
          if (!currentPath.isEmpty()) {
            // Remove duplicate vertex
            currentPath.remove(currentPath.size() - 1);
          }
          currentPath.addAll(pathSegment);
        }
        // Debug - print distance and their path
        // System.out.print(" - Distance from " + current.getId() + " to " + next.getId() + ": " + distance);
        totalDistance += distance;
      }
      // Debug - print total distance
      // System.out.println(" - Total Distance: " + totalDistance);
      if (validPath && totalDistance < shortestDistance) {
        shortestDistance = totalDistance;
        shortestPath = new ArrayList<>(currentPath);
      }
    }

    // Print shortest path and distance
    if (!shortestPath.isEmpty()) {
      System.out.println("Floyd-Warshall" + " Shortest Path Route:");
      for (int i = 0; i < shortestPath.size(); i++) {
        System.out.print(shortestPath.get(i).getId());
        if (i < shortestPath.size() - 1) {
          System.out.print(" -> ");
        }
      }
      System.out.println("\nFloyd-Warshall " + " Total Distance: " + shortestDistance);
    } else {
      System.out.println("No path found.");
    }
  }

  private static void findShorest(List<Vertex> destinations, Map<Vertex, ?> pairShortest, String algoName) {
    Vertex start = destinations.get(0);
    List<Vertex> destinationsPerm = new ArrayList<>(destinations);
    destinationsPerm.remove(start); // Remove start

    int shortestDistance = Integer.MAX_VALUE;
    List<Vertex> shortestPath = new ArrayList<>();
    //all permutations
    List<List<Vertex>> permutations = generatePermutations(destinationsPerm);

    for (List<Vertex> permutation : permutations) {
      permutation.add(0, start); // Add start
      int totalDistance = 0;
      List<Vertex> currentPath = new ArrayList<>();

      for (int i = 0; i < permutation.size() - 1; i++) {
        Vertex current = permutation.get(i);
        Vertex next = permutation.get(i + 1);
        Map<Vertex, Integer> distances = ((Result) pairShortest.get(current)).getDistances();
        List<Vertex> pathSegment = ((Result) pairShortest.get(current)).getPath(next);

        if (!pathSegment.isEmpty()) {
          if (!currentPath.isEmpty()) {
            // Remove duplicate
            currentPath.remove(currentPath.size() - 1);
          }
          currentPath.addAll(pathSegment);
        }
        totalDistance += distances.get(next);
      }
      if (totalDistance < shortestDistance) {
        shortestDistance = totalDistance;
        shortestPath = new ArrayList<>(currentPath);
      }
    }
    // Print shortest path and its distance
    System.out.println(algoName + " Shortest Path Route:");
    for (int i = 0; i < shortestPath.size(); i++) {
      System.out.print(shortestPath.get(i).getId());
      if (i < shortestPath.size() - 1) {
        System.out.print(" -> ");
      }
    }
    System.out.println("\n" + algoName + " Total Distance: " + shortestDistance);
  }


  // all permutations of vertices
  public static List<List<Vertex>> generatePermutations(List<Vertex> original) {
    if (original.size() == 0) {
      List<List<Vertex>> result = new ArrayList<>();
      result.add(new ArrayList<>());
      return result;
    }
    
    Vertex first = original.remove(0);
    List<List<Vertex>> ret = new ArrayList<>();
    List<List<Vertex>> permutations = generatePermutations(original);
    for (List<Vertex> perm : permutations) {
      for (int index = 0; index <= perm.size(); index++) {
        List<Vertex> temp = new ArrayList<>(perm);
        temp.add(index, first);
        ret.add(temp);
      }
    }
    return ret;
  }
}
