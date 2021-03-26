package me.r1411.maxcliques.objects;

import java.util.*;

public class Graph {
    private final Set<Vertex> vertices;
    private final Set<Edge> edges;

    public Graph() {
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public Graph(byte[][] matrix) {
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] == 1) {
                    if(!this.hasVertex(i))
                        this.addVertex(new Vertex(i));
                    if(!this.hasVertex(j))
                        this.addVertex(new Vertex(j));
                    this.addEdge(new Edge(this.getVertexByIndex(i), this.getVertexByIndex(j)));
                }
            }
        }
    }

    public Vertex getVertexByIndex(int index) {
        for(Vertex v : this.getVertices()) {
            if(v.getIndex() == index)
                return v;
        }

        return null;
    }

    public boolean hasVertex(int index) {
        return this.getVertexByIndex(index) != null;
    }

    public boolean hasEdge(Vertex from, Vertex to) {
        for(Edge e : this.getEdges())
            if(e.getVertexA().equals(from) && e.getVertexB().equals(to))
                return true;

        return false;
    }

    public Set<Vertex> getAdjacentTo(Vertex v_in) {
        Set<Vertex> result = new HashSet<>();
        for(Vertex v : this.getVertices()) {
            if(!v.equals(v_in) && this.hasEdge(v_in, v))
                result.add(v);
        }
        return result;
    }

    public Set<Vertex> getAdjacentToAll(Collection<Vertex> vertices) {
        Set<Vertex> result = new HashSet<>();
        for(Vertex v : this.getVertices()) {
            boolean ok = true;
            for(Vertex v_in : vertices) {
                if(!this.hasEdge(v, v_in)) {
                    ok = false;
                    break;
                }
            }
            if(ok) {
                result.add(v);
            }
        }
        return result;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }

    public Set<Vertex> getVertices() {
        return this.vertices;
    }

    public Set<Edge> getEdges() {
        return this.edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return getVertices().equals(graph.getVertices()) && getEdges().equals(graph.getEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVertices(), getEdges());
    }

    @Override
    public String toString() {
        return "Graph{" +
                "Vertices=" + vertices +
                ", Edges=" + edges +
                '}';
    }
}
