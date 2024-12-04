package aoc.shared.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Matrix {
    
    public static int[][] DIAGONALS = {
            {1, 1},  // down-right
            {1, -1}, // down-left
            {-1, 1}, // up-right
            {-1, -1} // up-left
        };
    
    public static int[][] ALL_DIRECTIONS = {
            {0, 1},  // right
            {1, 0},  // down
            {0, -1}, // left
            {-1, 0}, // up
            {1, 1},  // down-right
            {1, -1}, // down-left
            {-1, 1}, // up-right
            {-1, -1} // up-left
        };
    
    
    private List<List<Character>> matrix = new ArrayList<>();
    private int rowCount;
    private int colCount;

    public Matrix(List<List<Character>> matrix) {
        this.matrix = matrix;
        rowCount = matrix.size();
        if (rowCount > 0) {
            colCount = matrix.get(0).size();
        } else {
            colCount = 0;
        }
    }

    public static Matrix fromFile(Path file) throws IOException {
        List<List<Character>> m = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();
                List<Character> row = currentString.chars().mapToObj(c -> (char) c) // Map each int (char) to Character
                        .toList();
                m.add(row);
            }

        }

        return new Matrix(m);
    }

    public Character get(int row, int col) {
        if (row >= 0 && row < rowCount && col >= 0 && col < colCount) {
            return matrix.get(row).get(col);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    
    public Long countForPredicate(int row, int col, int distance, Predicate<String> predicate) {
        return  countForPredicate(row, col, distance, predicate, ALL_DIRECTIONS);
     }
    
    public Long countForPredicate(int row, int col, int distance, Predicate<String> predicate, int[][] directions) {
       return  generateStringForDirection(row, col, distance, directions).stream().filter(predicate).count();
    }
    
    public List<String> generateStringForDirection(int row, int col, int distance, int[][] directions) {
        List<String> allString = new ArrayList<>();
        

        for (int[] dir : directions) {
            int x = row, y = col;
            StringBuilder sb = new StringBuilder();            
            for (int step = 0; step < distance; step++) {
                // Check bounds
                if (x < 0 || x >= matrix.size() || y < 0 || y >= matrix.get(x).size()) {
                    break;
                }
                
                
                sb.append(get(x, y));
                
                // MOVE
                x += dir[0];
                y += dir[1];
            }
            
            
            if (sb.length() == distance) {
                allString.add(sb.toString());
            }
        }
        
        return allString;
    }
    
    

    public Stream<List<Character>> streamRows(){
        return matrix.stream();
    }


    public List<List<Character>> getMatrix() {
        return matrix;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    
    
}
