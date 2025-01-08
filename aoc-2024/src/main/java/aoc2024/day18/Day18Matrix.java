package aoc2024.day18;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;

public class Day18Matrix extends Matrix {
    private List<List<Integer>> costMatrix = new ArrayList<>();
    private List<List<List<String>>> pathMatrix = new ArrayList<>();
    
    public Day18Matrix(int row, int col) {
        super(row, col);
        for (int i = 0; i < row; i++) {
            this.pathMatrix.add(new ArrayList<>());
            for (int j = 0; j < col; j++) {
                this.pathMatrix.get(i).add(new ArrayList<>());
                if (this.costMatrix.size() == i) {
                    this.costMatrix.add(new ArrayList<>());
                }
                this.costMatrix.get(i).add(Integer.MAX_VALUE);
                
            }

        }
    }
    
    public int getCost(Position pos) {
        return getCost(pos.getRow(), pos.getCol());
    }

   

    public int getCost(int row, int col) {
        return costMatrix.get(row).get(col);
    }
    
    public void setCost(Position pos, int cost) {
        setCost(pos.getRow(), pos.getCol(), cost);
    }
    public void setCost(int row, int col, int cost) {
        costMatrix.get(row).set(col, cost);
    }

    public List<String> getPath(Position pos) {
        return getPath(pos.getRow(), pos.getCol());
    }
    
    public List<String> getPath(int row, int col) {
        return pathMatrix.get(row).get(col);
    }
    
    public void setPath(Position pos, String path) {
        setPath(pos.getRow(), pos.getCol(), path);
    }
    
    public void addPath(int row, int col, String path) {
        getPath(row, col).add(path);
    }
    
    public void setPath(int row, int col, String path) {
        getPath(row, col).clear();
        addPath(row,col, path);
    }
}
