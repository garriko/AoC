package aoc2024.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day16 {
    private class Answer {
        int minCost;
        List<Position> uniqueTiles;

        public Answer(int minCost, List<Position> uniqueTiles) {
            super();
            this.minCost = minCost;
            this.uniqueTiles = uniqueTiles;
        }

    }

    // 0 1 2 3
    // Directions: up, right, down, left
    static final int[] dRow = { -1, 0, 1, 0 };
    static final int[] dCol = { 0, 1, 0, -1 };
    static final int[] DIRECTIONS = { 0, 1, 2, 3 };

    public Integer calculatePart2(Day16Matrix m) {
        Position start = m.findFirstPositionFor('S').orElseThrow();
        Position exit = m.findFirstPositionFor('E').orElseThrow();
        return cheapestPath(m, start, exit).uniqueTiles.size();
    }

    public int calculatePart1(Day16Matrix m) {
        Position start = m.findFirstPositionFor('S').orElseThrow();
        Position exit = m.findFirstPositionFor('E').orElseThrow();

        return cheapestPath(m, start, exit).minCost;
    }


    private Answer cheapestPath(Day16Matrix m, Position start, Position exit) {

        PriorityQueue<Day16Position> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.getCost()));

        // direction initiale : droite
        Day16Position initPos = new Day16Position(start, 1, 0);
        queue.add(initPos);
        m.setCost(initPos, 0);
        m.getUsedTiles(initPos).add(Arrays.asList(start));

        while (!queue.isEmpty()) {
            Day16Position current = queue.poll();

//            if (current.getCost() > m.getCost(current)) {
//                // on a déjà un meilleur chemin
//                continue;
//            }

            // 0 1 2 3
            // Directions: up, right, down, left
            for (int direction : DIRECTIONS) {
                int newRow = current.getRow() + dRow[direction];
                int newCol = current.getCol() + dCol[direction];
	
                if (m.hasPosition(newRow, newCol) && !m.get(newRow, newCol).equals('#')) {
                    // If moving forward (same direction)
                    if (direction == current.getDirection()) {
                        int forwardCost = current.getCost() + 1;
                        List<Position> path = new ArrayList<>(current.getPath());
                        path.add(new Position(newRow, newCol));  
                        
                        if (forwardCost < m.getCost(newRow, newCol)) {
                            m.setCost(newRow, newCol, forwardCost);
                            
                            m.getUsedTiles(newRow, newCol).clear();
                            m.getUsedTiles(newRow, newCol).add(path);
                            
                            queue.add(new Day16Position(newRow, newCol, direction, forwardCost, new ArrayList<>(path)));
                        } else if (forwardCost == m.getCost(newRow, newCol)) {                                                        
                            m.getUsedTiles(newRow, newCol).add(path);
                            queue.add(new Day16Position(newRow, newCol, direction, forwardCost, new ArrayList<>(path)));
                        } else if (forwardCost == m.getCost(newRow, newCol) + 1000 ) {
                            queue.add(new Day16Position(newRow, newCol, direction, forwardCost, new ArrayList<>(path)));
                        }
                    }
                    // If turning (changing direction)
                    else {
                        int turnCost = current.getCost() + 1001;
                        List<Position> path = new ArrayList<>(current.getPath());
                        path.add(new Position(newRow, newCol));  
                        
                        if (turnCost < m.getCost(newRow, newCol)) {
                            m.setCost(newRow, newCol, turnCost);
                            
                            m.getUsedTiles(newRow, newCol).clear();   
                            m.getUsedTiles(newRow, newCol).add(path);
                            
                            queue.add(new Day16Position(newRow, newCol, direction, turnCost, new ArrayList<>(path)));
                        } else if (turnCost == m.getCost(newRow, newCol)) {
                            m.getUsedTiles(newRow, newCol).add(path);
                            queue.add(new Day16Position(newRow, newCol, direction, turnCost, new ArrayList<>(path)));
                        } 
                    }	
                }
            }
        }

        List<Position> bestPathTiles = m.getUsedTiles(exit).stream()
                .flatMap(List::stream) 
                .distinct()           
                .collect(Collectors.toList()); 

        for (Position pos : bestPathTiles) {
            m.set(pos, 'O');
        }

        return new Answer(m.getCost(exit), bestPathTiles);
    }
}
