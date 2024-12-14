package aoc2024.day14;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aoc.shared.model.Position;

public class Robot implements Comparable<Robot> {
    private static final String LINE_REGEX = "p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)";
    private Position initialPosition;
    private Position currentPosition;
    private int deltaRow;
    private int deltaCol;
    private int maxRow;
    private int maxCol;
    
    public static Robot fromLine(String input, int maxRow, int maxCol) {
        Robot r = new Robot(maxRow, maxCol);
        
        Pattern pattern = Pattern.compile(LINE_REGEX);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            Integer posCol = Integer.valueOf(matcher.group(1));
            Integer posRow = Integer.valueOf(matcher.group(2));
            r.deltaCol = Integer.valueOf(matcher.group(3));
            r.deltaRow = Integer.valueOf(matcher.group(4));
            r.initialPosition = new Position(posRow,posCol);
            r.currentPosition = new Position(posRow,posCol);
        }
        
        return r;
    }

    public Robot(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }
    
    public void calculatePositionAfterNIteration(int n) {
        int posRow = Math.floorMod(initialPosition.getRow() + n * deltaRow, maxRow);
        int posCol = Math.floorMod(initialPosition.getCol() + n * deltaCol, maxCol);
        
        currentPosition = new Position(posRow, posCol);
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int compareTo(Robot o) {
       return getCurrentPosition().compareTo(o.getCurrentPosition());
    }
    
    
}
