import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
  private Graph graph;
  private List<Vertex> destinations;
  private Random rand = new Random();

  public Generator(int numberOfVertices, int mode, int numberOfDestinations) {
    this.graph = new Graph();
    this.destinations = new ArrayList<>();
    generateVertices(numberOfVertices);
    generateEdges(numberOfVertices, mode);
    selectDestinations(numberOfDestinations, numberOfVertices);
  }

  private void generateVertices(int numberOfVertices) {
    for (int i = 0; i < numberOfVertices; i++) {
      graph.addVertex(new Vertex(i));
    }
  }

  private void generateEdges(int numberOfVertices, int mode) {
    List<Vertex> vertices = graph.getVertices();
    for (int i = 1; i < numberOfVertices; i++) {
      graph.addEdge(new Edge(vertices.get(i - 1), vertices.get(i), rand.nextInt(10) + 1)); // Random weight 1-10
    }

    // Calculate the total number of edges for the desired density
    double density = getDensity(mode);
    int totalEdges = (int)(density * numberOfVertices * (numberOfVertices - 1) / 2);
    int additionalEdges = totalEdges - (numberOfVertices - 1); // Subtract the spanning tree edges
    // Additional edges to meet desired density
    for (int i = 0; i < additionalEdges; i++) {
      int index1 = rand.nextInt(numberOfVertices);
      int index2 = rand.nextInt(numberOfVertices);
      Vertex vertex1 = vertices.get(index1);
      Vertex vertex2 = vertices.get(index2);
      // Ensure we don't create self-loops， duplicate edges， or edges that already exist
      while (index1 == index2 || graph.hasEdge(vertex1, vertex2)) {
        index1 = rand.nextInt(numberOfVertices);
        index2 = rand.nextInt(numberOfVertices);
        vertex1 = vertices.get(index1);
        vertex2 = vertices.get(index2);
      }
      graph.addEdge(new Edge(vertex1, vertex2, rand.nextInt(10) + 1)); // Random weight
    }
  }

  private double getDensity(int mode) {
    // 0: 10% density, 1: 30% density, 2: 60% density, 3: 90% density
    switch (mode) {
      case 0: return 0.1;
      case 1: return 0.3;
      case 2: return 0.6;
      case 3: return 0.9;
      default: return 0.3;
    }
  }

  private void selectDestinations(int numberOfDestinations, int numberOfVertices) {
    while (destinations.size() < numberOfDestinations) {
      Vertex v = graph.getVertices().get(rand.nextInt(numberOfVertices));
      if (!destinations.contains(v)) {
        destinations.add(v);
      }
    }
  }

  public Graph getGraph() {
    return graph;
  }

  public List<Vertex> getDestinations() {
    return destinations;
  }
}
