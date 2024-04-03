import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Result {
  private Map<Vertex, Integer> distances;
  private Map<Vertex, Vertex> predecessors;

  public Result(Map<Vertex, Integer> distances, Map<Vertex, Vertex> predecessors) {
    this.distances = distances;
    this.predecessors = predecessors;
  }

  public Map<Vertex, Integer> getDistances() {
    return distances;
  }

  public List<Vertex> getPath(Vertex destination) {
    LinkedList<Vertex> path = new LinkedList<>();
    Vertex step = destination;
    // Check if a path exists
    if (predecessors.get(step) == null) {
      return path; // Empty path means no path found
    }
    path.add(step);
    while (predecessors.get(step) != null) {
      step = predecessors.get(step);
      path.addFirst(step); // Add step at the beginning
    }
    return path;
  }

  public Map<Vertex, Vertex> getPredecessors() {
    return predecessors;
  }
}
