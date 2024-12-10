package aoc2024.day10;

import static aoc.shared.model.Matrix.CARDINAL_DIRECTIONS;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day10 {
    boolean part1;

    public Integer calculatePart1(Matrix input) {
        part1 = true;
        return findAllTrailhead(input);
    }
    
    public Integer calculatePart2(Matrix input) {
        part1 = false;
        return findAllTrailhead(input);
    }

    private int findAllTrailhead(Matrix input) {
        int pathSum = 0;
        for(int row = 0; row < input.getRowCount();row++ ) {
            for(int col = 0; col < input.getColCount(); col++) {
                // on part des 0 
                if('0' == input.get(row, col)) {
                    //
                    //List<Position> visited = new ArrayList<>();
                    List<Position> visitedNineList = new ArrayList<>();
                    Path currentPath = new Path(new Position(row,col));      
                    List<Path> validPath = new ArrayList<Path>();
                   findAllPathForStartingPosition(input, 0, row, col, currentPath, Matrix.CARDINAL_DIRECTIONS, validPath, visitedNineList);
                    pathSum += validPath.size();
                }
            }
        }
        return pathSum;
    }

    // recursive, recherche en profondeur
    private void findAllPathForStartingPosition(Matrix m, int currentNumber, int row, int col, Path currentPath,
            int[][] directionToCheck, List<Path> validPathList, List<Position> visitedNineList) {

        for (int[] direction : directionToCheck) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];

            if (m.hasPosition(nextRow, nextCol)) {
                int nextNumber = Character.getNumericValue(m.get(nextRow, nextCol));

                if (nextNumber == currentNumber + 1) {
                    // le path continue

                    Position nextPosition = new Position(nextRow, nextCol);
                    if (nextNumber == 9) {
                        // fin du path                        
                        if(part1) {
                            if(!visitedNineList.contains(nextPosition)) {
                                currentPath.add(nextPosition);
                                validPathList.add(currentPath);
                                visitedNineList.add(nextPosition);
                            }
                        } else {
                            currentPath.add(nextPosition);
                            validPathList.add(currentPath);
                        }
                        
                    } else {
                        // on continue à chercher

                        findAllPathForStartingPosition(m, 
                                nextNumber,
                                nextRow, 
                                nextCol,
                                newPathToCheck(currentPath, nextPosition),
                                getOtherDirection( direction), 
                                validPathList,
                                visitedNineList);

                    }

                }
            }
        }

    }

    private Path newPathToCheck(Path currentPath, Position position) {
        Path copy = new Path(currentPath);
        copy.add(position);
        return copy;
    }

    private int[][] getOtherDirection(int[] directionToRemove) {
        int[][] otherDirections = new int[CARDINAL_DIRECTIONS.length - 1][];
        int newIndex = 0;

        // Copy all directions except the one to be removed
        for (int i = 0; i < CARDINAL_DIRECTIONS.length; i++) {
            if (CARDINAL_DIRECTIONS[i][0] == (-1 * directionToRemove[0]) && CARDINAL_DIRECTIONS[i][1] == (-1 * directionToRemove[1])) {
                continue; // Skip the direction to remove
            }
            otherDirections[newIndex++] = CARDINAL_DIRECTIONS[i];
        }

        return otherDirections;
    }

}
