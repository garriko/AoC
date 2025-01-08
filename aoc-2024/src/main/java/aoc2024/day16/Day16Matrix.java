package aoc2024.day16;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;

public class Day16Matrix extends Matrix {
    // cout de chaque cellule pour chaque direction
    // Directions: up, right, down, left
    private int[][] cellCost;
    private List<List<Position>>[][] usedTiles;

    @SuppressWarnings("unchecked")
    public Day16Matrix(List<List<Character>> matrix) {
        super(matrix);
        cellCost = new int[matrix.size()][matrix.get(0).size()];
        usedTiles= new ArrayList[getRowCount()][getColCount()];
        initCellCost();
    }

    @SuppressWarnings("unchecked")
    public Day16Matrix(Matrix m) {
        super(m);
        cellCost = new int[m.getRowCount()][m.getColCount()];
        usedTiles= new ArrayList[getRowCount()][getColCount()];
        initCellCost();
    }

    // initialise chaque cellule au max
    private void initCellCost() {
        for (int i = 0; i < getRowCount(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                for (int k = 0; k < 4; k++) {
                    cellCost[i][j] = Integer.MAX_VALUE;
                    usedTiles[i][j] = new ArrayList<>();
                }
            }
        }
    }

    public void setCost(Position pos, int cost) {
        setCost(pos.getRow(), pos.getCol(), cost);
    }

    public void setCost(int row, int col, int cost) {
        cellCost[row][col]= cost;        
    }

    public int getCost(Day16Position pos) {      
        return getCost(pos.getRow(), pos.getCol());
    }
    
    public int getCost(Position pos) {
        return getCost(pos.getRow(), pos.getCol());
    }
    
    public int getCost(int row, int col) {
        return cellCost[row][col];
    }
    
    public List<List<Position>> getUsedTiles(Position initPos) {
       return getUsedTiles(initPos.getRow(), initPos.getCol());
        
    }

    public List<List<Position>> getUsedTiles(int row, int col) {
       return usedTiles[row][col];
    }
}
