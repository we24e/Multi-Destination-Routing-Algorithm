import java.util.ArrayList;
import java.util.List;

public class GraphConstructor {
  private Graph graph;
  private List<Vertex> destinations;

  public GraphConstructor() {
    this.graph = new Graph();
    this.destinations = new ArrayList<>();
  }
  public void constructSampleGraph() {
    Vertex v0 = new Vertex(0);
    Vertex v1 = new Vertex(1);
    Vertex v2 = new Vertex(2);
    Vertex v3 = new Vertex(3);
    Vertex v4 = new Vertex(4);
    Vertex v5 = new Vertex(5);
    Vertex v6 = new Vertex(6);

    graph.addVertex(v0);
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addVertex(v5);
    graph.addVertex(v6);

    graph.addEdge(new Edge(v0, v1, 2));
    graph.addEdge(new Edge(v0, v2, 4));
    graph.addEdge(new Edge(v1, v2, 1));
    graph.addEdge(new Edge(v1, v3, 7));
    graph.addEdge(new Edge(v2, v4, 3));
    graph.addEdge(new Edge(v3, v4, 2));
    graph.addEdge(new Edge(v3, v5, 1));
    graph.addEdge(new Edge(v4, v5, 5));
    graph.addEdge(new Edge(v4, v6, 1));
    graph.addEdge(new Edge(v5, v6, 7));
    graph.addEdge(new Edge(v2, v3, 1));

    destinations.add(v0); // Start
    destinations.add(v5);
    destinations.add(v6);
    destinations.add(v4);
  }

  //  public void constructComplexGraph() {}
  public void GraphApp(int numberOfVertices, int mode, int numberOfDestinations) {
    Generator generator = new Generator(numberOfVertices, mode, numberOfDestinations);
    this.graph = generator.getGraph();
    this.destinations = generator.getDestinations();
    displayGraph();
  }
  public void displayGraph() {
    System.out.println("--------------------");
    System.out.println("Graph has " + graph.getVertices().size() + " vertices.");
    System.out.println("Graph has " + graph.getEdges().size() + " edges.");
    System.out.println("Destinations:");
    for (Vertex destination : destinations) {
      System.out.println(destination.getId());
    }
    System.out.println("--------------------");
  }
  public Graph getGraph() {
    return graph;
  }
  public List<Vertex> getDestinations() {
    return destinations;
  }
}
