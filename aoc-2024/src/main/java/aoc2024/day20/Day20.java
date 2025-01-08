package aoc2024.day20;

import static aoc.shared.model.Matrix.CARDINAL_DIRECTIONS;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import aoc2024.day12.Day12Matrix;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day20 {

    public Integer calculatePart1(Matrix input, int savedTimeMin) {
        Day12Matrix m = new Day12Matrix(input);
        Position start = m.findFirstPositionFor('S').orElseThrow();
        Position exit = m.findFirstPositionFor('E').orElseThrow();

        List<Day20Position> path = new ArrayList<>();
        dfs(m, new Day20Position(start),  new Day20Position(exit), path);

        int cheatScore = 0;
        int[] previousDirection = null;
        for (int currentIndex = 0; currentIndex < path.size(); currentIndex++) {
            Day20Position currentPos = path.get(currentIndex);

            int row = currentPos.getRow();
            int col = currentPos.getCol();

            for (int j = 0; j < CARDINAL_DIRECTIONS.length; j++) {
                if (CARDINAL_DIRECTIONS[j] != currentPos.getNextDirection()
                        && CARDINAL_DIRECTIONS[j] != previousDirection) {
                    // check direction
                    int[] currentDir = CARDINAL_DIRECTIONS[j];

                    if (directionEquals(currentDir,Matrix.UP)) {
                        if (canCheat(m, row - 2, col) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row - 2, col))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row - 1, col + 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row - 1, col + 1))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row - 1, col - 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row - 1, col - 1))) >= savedTimeMin) {
                            cheatScore++;
                        }
                    } else if (directionEquals(currentDir, Matrix.RIGHT)) {
                        if (canCheat(m, row, col + 2) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row, col + 2))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row - 1, col + 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row - 1, col + 1))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row + 1, col + 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row + 1, col + 1))) >= savedTimeMin) {
                            cheatScore++;
                        }
                    } else if (directionEquals(currentDir, Matrix.DOWN)) {
                        if (canCheat(m, row + 2, col) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row + 2, col))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row + 1, col - 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row + 1, col - 1))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row + 1, col + 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row + 1, col + 1))) >= savedTimeMin) {
                            cheatScore++;
                        }
                    } else if (directionEquals(currentDir, Matrix.LEFT)) {
                        if (canCheat(m, row, col - 2) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row, col - 2))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row - 1, col - 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row - 1, col - 1))) >= savedTimeMin) {
                            cheatScore++;
                        }

                        if (canCheat(m, row + 1, col - 1) && calculateTimeSaved(currentIndex,
                                path.indexOf(new Day20Position(row + 1, col - 1))) >= savedTimeMin) {
                            cheatScore++;
                        }
                    }
                }
            }

            previousDirection = getOppositeDirection(currentPos.getNextDirection());
        }

        return cheatScore;
    }

    private boolean directionEquals(int[] currentDir, int[] other) {
        if(null == currentDir || currentDir.length != other.length) {
            return false;
        }
        boolean equals = true;
        // Iterate through the original array and negate each value
        for (int i = 0; i < currentDir.length; i++) {
            equals = equals && currentDir[i] == other[i];
        }

        return equals;
    }

    private int calculateTimeSaved(int currentIndex, int cheatIndex) {
        // pas de temps gagné
        if (cheatIndex < currentIndex) {
            return -1;
        }

        return cheatIndex - currentIndex - 2;

    }

    private boolean canCheat(Matrix m, int row, int col) {
        return m.hasPosition(row - 2, col) && (m.get(row - 2, col).equals('.') || m.get(row - 2, col).equals('E'));
    }

    // Method to return a new int array with opposite values
    private int[] getOppositeDirection(int[] original) {
        if(null == original) {
            return null;
        }
        // Create a new array of the same length
        int[] opposite = new int[original.length];

        // Iterate through the original array and negate each value
        for (int i = 0; i < original.length; i++) {
            opposite[i] = -original[i];
        }

        return opposite;
    }

    // DFS to find the path
    private boolean dfs(Day12Matrix m, Day20Position currentPosition, Day20Position exit, List<Day20Position> path) {
        // Base case: If out of bounds or already visited or a wall
        if (!m.hasPosition(currentPosition) || m.isVisited(currentPosition) || m.get(currentPosition).equals('#')) {
            return false;
        }

        // Mark this cell as visited
        m.visit(currentPosition);

        path.add(currentPosition);

        // If we've reached the exit, return true
        if (currentPosition.equals(exit)) {
            return true;
        }

        // Explore all 4 possible directions (up, right, down, left)
        for (int i = 0; i < CARDINAL_DIRECTIONS.length; i++) {
            int newRow = currentPosition.getRow() + CARDINAL_DIRECTIONS[i][0];
            int newCol = currentPosition.getCol() + CARDINAL_DIRECTIONS[i][1];

            if (dfs(m, new Day20Position(newRow, newCol), exit, path)) {
                currentPosition.setNextDirection(CARDINAL_DIRECTIONS[i]);
                return true;
            }
        }

        // Backtrack if no path found, remove the current position from the path
        path.removeLast();
        return false;
    }

}
