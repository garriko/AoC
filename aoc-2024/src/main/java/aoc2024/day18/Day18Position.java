package aoc2024.day18;

import aoc.shared.model.Position;

public class Day18Position extends Position {
    private int cost;
    private String path;

    public Day18Position(int row, int col, int cost) {
        super(row, col);
        this.cost = cost;
        path = "";
    }

    public Day18Position(Position pos, int cost) {
        super(pos);
        this.cost = cost;
        path = "";
    }

    public Day18Position(int row, int col, int cost, String path) {
        super(row, col);
        this.cost = cost;
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return super.toString() + " = " + cost;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPreviousDirection() {
        if (path.length() > 0) {
            return String.valueOf(path.charAt(path.length() - 1));
        } else {
            return "";
        }
    }

}
