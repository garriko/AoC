package aoc2024.day23;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import aoc.shared.model.Graph;
import aoc.shared.model.Vertex;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day23 {

    public long calculatePart1(List<String> input) {
        Graph g = new Graph();

        for (String line : input) {
            String[] computerNameList = line.split("-");
            g.addEdge(computerNameList[0], computerNameList[1]);
        }

        return getConnectedVertex(g, 3).stream().filter(connectedVertex -> {
            return connectedVertex.stream().anyMatch(vertex -> vertex.getName().startsWith("t"));
        }).count();
    }
    
    public String calculatePart2(List<String> input) {
        Graph g = new Graph();

        for (String line : input) {
            String[] computerNameList = line.split("-");
            g.addEdge(computerNameList[0], computerNameList[1]);
        }
        
        ArrayList<Set<Vertex>> cliques = new ArrayList<>();
        List<Vertex> potential_clique = new ArrayList<>();
        List<Vertex> candidates = new ArrayList<>();
        List<Vertex> already_found = new ArrayList<>();
        
        candidates.addAll(g.keySet());

        findCliques(potential_clique, candidates, already_found, cliques, g);
        
        Optional<Set<Vertex>> biggestClique = cliques.stream().max(Comparator.comparingInt(Set::size));
        
        return biggestClique.orElseThrow().stream().map(Vertex::getName).sorted().collect(Collectors.joining(","));
    }
    
    
    // implémentation java de l'algo de bron-kerbosch
    private void findCliques(List<Vertex> potentialClique, List<Vertex> candidates, List<Vertex> alreadyFound, List<Set<Vertex>> cliques, Graph g) {
        List<Vertex> candidatesArray = new ArrayList<>(candidates);
        
        if (!end(candidates, alreadyFound, g)) {
           
            for (Vertex candidate : candidatesArray) {
                List<Vertex> newCandidates = new ArrayList<>();
                List<Vertex> newAlreadyFound = new ArrayList<>();

              
                potentialClique.add(candidate);
                candidates.remove(candidate);
                
                for (Vertex newCandidate : candidates) {
                    if (g.isNeighbor(candidate, newCandidate))
                    {
                        newCandidates.add(newCandidate);
                    } 
                }
               
                for (Vertex newFound : alreadyFound) {
                    if (g.isNeighbor(candidate, newFound)) {
                        newAlreadyFound.add(newFound);
                    } 
                }

                
                if (newCandidates.isEmpty() && newAlreadyFound.isEmpty()) {
                    
                    cliques.add(new HashSet<>(potentialClique));
                }                 
                else {
                    // recursive call
                    findCliques(
                        potentialClique,
                        newCandidates,
                        newAlreadyFound, 
                        cliques, 
                        g);
                } 
                
                alreadyFound.add(candidate);
                potentialClique.remove(candidate);
            }
        }
        
    }
    

    

    private boolean end(List<Vertex> candidates, List<Vertex> already_found, Graph g) {
     
        boolean end = false;
        int edgecounter;
        for (Vertex found : already_found) {
            edgecounter = 0;
            for (Vertex candidate : candidates) {
                if (g.isNeighbor(found, candidate)) {
                    edgecounter++;
                } 
            } 
            if (edgecounter == candidates.size()) {
                end = true;
            }
        } 
        return end;
    }

    private List<Set<Vertex>> getConnectedVertex(Graph g, int connectionSize) {
        List<Set<Vertex>> connectSet = new ArrayList<>();

        generateCombinations(new ArrayList<>(g.keySet()), connectionSize, 0, new HashSet<>(), connectSet, g);

        return connectSet;

    }

    // Helper function to generate all combinations of `k` nodes and check for
    // connectivity
    private void generateCombinations(List<Vertex> nodes, int connectionSize, int start, Set<Vertex> current,
            List<Set<Vertex>> connectedSubgraphs, Graph graph) {
        if (current.size() == connectionSize) {
            // Check if the current set of nodes forms a connected subgraph
            if (isConnected(graph, current)) {
                connectedSubgraphs.add(new HashSet<>(current));
            }
            return;
        }

        for (int i = start; i < nodes.size(); i++) {
            current.add(nodes.get(i));
            generateCombinations(nodes, connectionSize, i + 1, current, connectedSubgraphs, graph);
            current.remove(nodes.get(i)); // backtrack
        }
    }

    private boolean isConnected(Graph graph, Set<Vertex> vertexSet) {
        boolean allConnected = true;
        for(Vertex v : vertexSet) {
            List<Vertex> neighbourList = graph.getAdjVertices(v);
            List<Vertex> list = new ArrayList<>(vertexSet);
            list.remove(v);
            
            allConnected = allConnected && neighbourList.containsAll(list);
            
            if(!allConnected) {
                break;
            }
        }
        
        return allConnected;
    }
}
