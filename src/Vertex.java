import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private int id;
    private List<Edge> edges = new ArrayList<>();
    public Vertex(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public List<Edge> getEdges() {
        return edges;
    }
    public void addEdge(Edge edge) {
        edges.add(edge);
    }
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }
    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
    }
    public boolean hasEdge(Vertex vertex) {
        for (Edge edge : edges) {
            if (edge.getVertex1().equals(vertex) || edge.getVertex2().equals(vertex)) {
                return true;
            }
        }
        return false;
    }
}
