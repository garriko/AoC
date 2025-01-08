package aoc.shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph extends HashMap<Vertex, List<Vertex>> {
    private static final long serialVersionUID = 7678576209391915258L;

    public void addVertex(String label) {
        putIfAbsent(new Vertex(label), new ArrayList<>());
    }
    
    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        values().stream().forEach(e -> e.remove(v));
        remove(new Vertex(label));
    }
    
    public void addEdge(String label1, String label2) {
        addVertex(label1);
        addVertex(label2);
        
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);      
        get(v1).add(v2);
        get(v2).add(v1);
    }
    
    public List<Vertex> getAdjVertices(String label) {
        return get(new Vertex(label));
    }
    
    public List<Vertex> getAdjVertices(Vertex v) {
        return get(v);
    }

    public boolean isNeighbor(Vertex v1, Vertex v2) {
        return get(v1).contains(v2);
    }
   
}
