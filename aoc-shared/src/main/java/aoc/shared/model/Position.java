package aoc.shared.model;

import java.util.Objects;

public class Position implements Comparable<Position> {
    private int row;
    private int col;

    public Position(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public Position(Position pos) {
        this.row = pos.row;
        this.col = pos.col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void move(int[] direction) {
        row += direction[0];
        col += direction[1];
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        return col == other.col && row == other.row;
    }

    @Override
    public String toString() {
        return "Position ("+ row + ","+ col + ")";
    }

    @Override
    public int compareTo(Position o) {
        int compareRow = Integer.compare(row, o.row);
        if(0 != compareRow) {
            return compareRow;
        } else {
            return Integer.compare(col, o.col);
        }
        
    }
    

}
