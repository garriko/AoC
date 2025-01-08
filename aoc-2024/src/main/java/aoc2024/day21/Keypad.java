package aoc2024.day21;

import java.util.Comparator;
import java.util.PriorityQueue;

import aoc2024.day18.Day18Matrix;
import aoc2024.day18.Day18Position;

public abstract class Keypad extends Day18Matrix {
    public static int times = 0;
    protected static final char OBSTACLE = '#';
    public Keypad(int i, int j) {
        super(i, j);
    }

    protected String directionToString(int[] direction) {
        if (direction[0] == 0 && direction[1] == 1) {
            return ">";
        } else if (direction[0] == 1 && direction[1] == 0) {
            return "v";
        } else if (direction[0] == 0 && direction[1] == -1) {
            return "<";
        } else {
            return "^";
        }
    }
    
       
    public String findShortestPath(char startChar, char endChar) {
        PriorityQueue<Day18Position> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.getCost()));
        times++;
        
        Day18Position start = new Day18Position(findFirstPositionFor(startChar).orElseThrow(), 0);
        Day18Position exit = new Day18Position(findFirstPositionFor(endChar).orElseThrow(), 0);
        queue.add(start);
        setCost(start, 0);
        

        while (!queue.isEmpty()) {
            Day18Position current = queue.poll();

            if (current.getCost() > getCost(current)) {
                // on a déjà un meilleur chemin
                continue;
            }

            for (int[] direction : CARDINAL_DIRECTIONS) {
                int newRow = current.getRow() + direction[0];
                int newCol = current.getCol() + direction[1];
    
                if (hasPosition(newRow, newCol) && !get(newRow, newCol).equals(OBSTACLE)) {         
                    String directionStr = directionToString(direction);
                    
                    int newCost = current.getCost() + 2;
                    if(directionStr.equals(current.getPreviousDirection())) {
                        newCost--;
                    }
                    
                    
                    String path = current.getPath() + directionStr;
                    if (newCost < getCost(newRow, newCol)) {
                        setCost(newRow, newCol, newCost);
                        setPath(newRow, newCol, path);
                        queue.add(new Day18Position(newRow, newCol, newCost, path));
                    } else if(newCost == getCost(newRow, newCol)) {
                        addPath(newRow, newCol, path);
                        queue.add(new Day18Position(newRow, newCol, newCost, path));
                    }
                }
            }
        }

        return findBestPath(exit);
    }

    private String findBestPath(Day18Position exit) {
        String bestPath = null;
        
        for(String path: getPath(exit)) {
            if(null == bestPath) {
                bestPath = path;
            } else {
                if(path.startsWith("<")) {
                    bestPath = path;
                } else if(!bestPath.startsWith("<") && path.startsWith("v")) {
                    bestPath = path;
                } else if(!bestPath.startsWith("<") && !bestPath.startsWith("v") && path.startsWith("^")) {
                    bestPath = path;
                }
            }
        }
        
        return bestPath;
    }
    
    
}
