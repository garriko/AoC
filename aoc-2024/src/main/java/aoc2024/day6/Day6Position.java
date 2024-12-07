package aoc2024.day6;

import java.util.Arrays;

import aoc.shared.model.Position;

public class Day6Position extends Position {
    private int[] direction;

    public Day6Position(int row, int col, int[] direction) {
        super(row, col);
        this.direction = direction;
    }

    public Day6Position(Position pos, int[] direction) {
        super(pos);
        this.direction = direction;
    }
    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) {
        this.direction = direction;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(direction);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Day6Position other = (Day6Position) obj;
        return Arrays.equals(direction, other.direction);
    }
}
