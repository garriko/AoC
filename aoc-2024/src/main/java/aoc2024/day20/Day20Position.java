package aoc2024.day20;

import java.util.Arrays;

import aoc.shared.model.Position;

public class Day20Position extends Position {
    private int[] nextDirection;
    public Day20Position(Position pos) {
        super(pos);
    }
    public Day20Position(int row, int col) {
        super(row,col);
    }
    
    public int[] getNextDirection() {
        return nextDirection;
    }
    public void setNextDirection(int[] nextDirection) {
        this.nextDirection = nextDirection;
    }
    @Override
    public int hashCode() {
       return super.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    
    
}
