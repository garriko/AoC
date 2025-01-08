package aoc2024.day16;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Position;

public class Day16Position extends Position {
    // Directions: up, right, down, left
    private int direction;
    private int cost;
    private List<Position> path;

    
    public Day16Position(int row, int col, int dir, int cost, List<Position> path) {
        super(row, col);
        this.direction = dir;
        this.cost = cost;
        this.path = path;
    }

    public Day16Position(Position pos, int dir, int cost, List<Position> path) {
        super(pos);
        this.direction = dir;
        this.cost = cost;
        this.path = path;
    }

    public Day16Position(Position pos, int dir, int cost) {
        super(pos);
        this.direction = dir;
        this.cost = cost;
        this.path = new ArrayList<>();
        path.add(pos);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    public List<Position> getPath() {
        return path;
    }

    public void setPath(List<Position> path) {
        this.path = path;
    }

    @Override
    public String toString() {        
        return super.toString() + ", Direction " + direction + " = " + cost;
    }

    
}
