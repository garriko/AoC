package aoc2024.day15;

import java.util.Objects;

import aoc.shared.model.Position;

public class Box implements Comparable<Box> {
    private Position leftBracket;
    private Position rightBracket;
    public Position getLeftBracket() {
        return leftBracket;
    }
    public void setLeftBracket(Position leftBracket) {
        this.leftBracket = leftBracket;
    }
    public Position getRightBracket() {
        return rightBracket;
    }
    public void setRightBracket(Position rightBracket) {
        this.rightBracket = rightBracket;
    }
    @Override
    public int hashCode() {
        return Objects.hash(leftBracket, rightBracket);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Box other = (Box) obj;
        return Objects.equals(leftBracket, other.leftBracket) && Objects.equals(rightBracket, other.rightBracket);
    }
    
    @Override
    public int compareTo(Box o) {
        return leftBracket.compareTo(o.leftBracket);
    }
    
    
}
