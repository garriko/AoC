package aoc.shared.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Matrix {

    public static int[][] DIAGONALS = { { 1, 1 }, // down-right
            { 1, -1 }, // down-left
            { -1, 1 }, // up-right
            { -1, -1 } // up-left
    };
    
    public static int[][] CARDINAL_DIRECTIONS = {
            { 0, 1 }, // right
            { 1, 0 }, // down
            { 0, -1 }, // left
            { -1, 0 }, // up
    };

    public static int[][] ALL_DIRECTIONS = { { 0, 1 }, // right
            { 1, 0 }, // down
            { 0, -1 }, // left
            { -1, 0 }, // up
            { 1, 1 }, // down-right
            { 1, -1 }, // down-left
            { -1, 1 }, // up-right
            { -1, -1 } // up-left
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

    public Matrix(Matrix m) {
        for(int i = 0; i < m.getRowCount(); i++ ) {
            for(int j = 0; j < m.getColCount(); j++ ) {
                if(this.matrix.size() == i) {
                    this.matrix.add(new ArrayList<>());
                }
                this.matrix.get(i).add(m.matrix.get(i).get(j));
            }
            
        }
       
        this.rowCount = m.rowCount;
        this.colCount = m.colCount;
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

    public Optional<Position> findFirstPositionFor(Character c) {
        if (null == c) {
            return null;
        }

        return IntStream.range(0, matrix.size())
            .mapToObj(row -> {
                List<Character> rowList = matrix.get(row);
                return IntStream.range(0, rowList.size())
                        .filter(col -> rowList.get(col) == c)
                        .mapToObj(col -> new Position(row, col));
            })
            .flatMap(s->s)
            .findFirst();

    }

    public Character get(Position pos) {
        return get(pos.getRow(), pos.getCol());
    }
    

    public void set(Position position, char c) {
        
            matrix.get(position.getRow()).set(position.getCol(), c);
        
    }
    

    public boolean hasPosition(Position currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getCol();
        return row >= 0 && row < rowCount && col >= 0 && col < colCount;
     
    }
    
    public boolean hasPosition(int row, int col) {
       return hasPosition(new Position(row, col));
    }

    public Long countForPredicate(int row, int col, int distance, Predicate<String> predicate) {
        return countForPredicate(row, col, distance, predicate, ALL_DIRECTIONS);
    }

    public Long countForPredicate(int row, int col, int distance, Predicate<String> predicate, int[][] directions) {
        return generateStringForDirection(row, col, distance, directions).stream().filter(predicate).count();
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

    public Stream<List<Character>> streamRows() {
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(List<Character> row : matrix) {
            for(Character c : row) {
                sb.append(c);
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
