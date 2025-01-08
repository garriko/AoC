package aoc2024.day18;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day18 {
    

    private static final char OBSTACLE = '#';

    public String calculatePart2(List<String> input, int row, int col) {
 
        Day18Position start = new Day18Position(0, 0, 0);
        Position exit = new Position(row - 1, col - 1);
        
        int inf = 0;
        int max = input.size();
        Day18Matrix m;
        do {
            int elapsed = (max + inf) / 2;
            m = new Day18Matrix(row, col);
            fillMatrix(m, input, elapsed);
            
            if(findShortestPath(m, start, exit) == -1) {
                max = elapsed;
            } else {
                inf = elapsed + 1;
            }
            
            
        } while(inf != max);
        
        
        
        
        return input.get(inf - 1);
    }
    
    public Integer calculatePart1(List<String> input, int row, int col, int elapsed) {
        
        Day18Matrix m = new Day18Matrix(row, col);
        Day18Position start = new Day18Position(0, 0, 0);
        Position exit = new Position(row - 1, col - 1);
        
        fillMatrix(m, input, elapsed);
        
        int shortestPath = findShortestPath(m, start, exit);
        
        
        return shortestPath;
    }
    
    private void fillMatrix(Matrix m, List<String> input, int elapsed) {
        for(int i = 0; i < elapsed; i++) {
            var obsPosition = input.get(i).split(",");
            int colObs = Integer.parseInt(obsPosition[0]);
            int rowObs = Integer.parseInt(obsPosition[1]);
            
            m.set(rowObs, colObs, '#');
        }        
    }


    private int findShortestPath(Day18Matrix m, Day18Position start, Position exit) {
        PriorityQueue<Day18Position> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.getCost()));

        queue.add(start);
        m.setCost(start, 0);
        

        while (!queue.isEmpty()) {
            Day18Position current = queue.poll();

            if (current.getCost() > m.getCost(current)) {
                // on a déjà un meilleur chemin
                continue;
            }

            for (int[] direction : Matrix.CARDINAL_DIRECTIONS) {
                int newRow = current.getRow() + direction[0];
                int newCol = current.getCol() + direction[1];
    
                if (m.hasPosition(newRow, newCol) && !m.get(newRow, newCol).equals(OBSTACLE)) {
                   
                    int newCost = current.getCost() + 1;
                    if (newCost < m.getCost(newRow, newCol)) {
                        m.setCost(newRow, newCol, newCost);                       
                        queue.add(new Day18Position(newRow, newCol, newCost));
                    } 
                    
                  
                }
            }
        }

        return m.getCost(exit);
     
    }


}
