import java.util.ArrayList;
import java.util.List;

public class Graph {
  private List<Vertex> vertices = new ArrayList<>();
  private List<Edge> edges = new ArrayList<>();

  public List<Vertex> getVertices() {
    return vertices;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public void addVertex(Vertex vertex) {
    vertices.add(vertex);
  }

  public void removeVertex(Vertex vertex) {
    vertices.remove(vertex);
  }

  public void addEdge(Edge edge) {
    edges.add(edge);
    edge.getVertex1().addEdge(edge);
    edge.getVertex2().addEdge(edge);
    // add for undirected
    edges.add(new Edge(edge.getVertex2(), edge.getVertex1(), edge.getWeight()));

  }

  public void removeEdge(Edge edge) {
    edges.remove(edge);
    edge.getVertex1().removeEdge(edge);
    edge.getVertex2().removeEdge(edge);
  }

  // debug toString
  public String toString() {
    String str = "";
    for (Vertex vertex : vertices) {
      str += vertex.getId() + ": ";
      for (Edge edge : vertex.getEdges()) {
        str += edge.getVertex1().getId() + "-" + edge.getVertex2().getId() + " ";
      }
      str += "\n";
    }
    return str;
  }

  public Boolean hasEdge(Vertex vertex1, Vertex vertex2) {
    for (Edge edge : edges) {
      if ((edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) || (edge.getVertex1().equals(vertex2) && edge.getVertex2().equals(vertex1))) {
        return true;
      }
    }
    return false;
  }
}
