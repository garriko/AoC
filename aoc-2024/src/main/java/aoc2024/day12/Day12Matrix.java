package aoc2024.day12;

import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import io.quarkus.logging.Log;

public class Day12Matrix extends Matrix {
    private boolean[][] visitedArray;
    public Day12Matrix(List<List<Character>> matrix) {
        super(matrix);
        visitedArray = new boolean[matrix.size()][matrix.get(0).size()];
    }
    public Day12Matrix(Matrix m) {
        super(m);
        visitedArray = new boolean[m.getRowCount()][m.getColCount()];
    }
    
    public boolean isVisited(int row, int col) {
        return hasPosition(row, col) && visitedArray[row][col] ;
    }
    
    public boolean isVisited(Position pos) {
        return hasPosition(pos) && visitedArray[pos.getRow()][pos.getCol()] ;
    }
    
    public void visit(int row, int col) {
        if(!hasPosition(row, col)) {
            Log.error("Position out of bounds !!!!!");
            return;
        }
        
        visitedArray[row][col] = true;
    }
    
    public void visit(Position pos) {
        if(!hasPosition(pos)) {
            Log.error("Position out of bounds !!!!!");
            return;
        }
        
        visitedArray[pos.getRow()][pos.getCol()] = true;
    }
}
