package aoc2024.day6;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day6 {

    private static int[] UP    = { -1, 0 };
    private static int[] RIGHT = { 0, 1 };
    private static int[] DOWN  = { 1, 0 };
    private static int[] LEFT  = { 0, -1 };
    private static Character OBSTACLE  = '#';

    /**
     * Calcul du chemin suivi par le garde
     */
    public Integer calculatePart1(Matrix input) {
        int[] currentDirection = UP;
        Day6Matrix m = new Day6Matrix(input);

        Position startingPos = m.findGuardStartingPosition();
        List<Position> walkedPosition = getGuardPathPart1(m, startingPos, currentDirection);
        
        return walkedPosition.size();
    }
    


    private List<Position> getGuardPathPart1(Day6Matrix m, Position startingPos, int[] currentDirection) {
        List<Position> walkedPosition = new ArrayList<>();
        
        walkedPosition.add(new Position(startingPos));
        
        Position currentPosition = new Position(startingPos);
        boolean keepGoing = true;
        while(keepGoing) {
            Position nextPosition = getNextPosition(currentPosition, currentDirection);
            if (m.hasPosition(nextPosition)) {
                    if (m.get(nextPosition) == OBSTACLE) {
                        // tourne
                        currentDirection = pivot(currentDirection);
                    } else {
                        if(!walkedPosition.contains(nextPosition)) {                    
                            walkedPosition.add(nextPosition);
                            currentPosition = nextPosition;
                        } 
                    }
                
            } else {
                keepGoing = false;
            }            
        }
        
        return walkedPosition;
    }



    public Integer calculatePart2(Matrix input) {
        int[] currentDirection = UP;        
        Day6Matrix m = new Day6Matrix(input);
        Position startingPos = m.findGuardStartingPosition();
        List<Position> loopPositionList = new ArrayList<Position>();
        List<Day6Position> walkedPosition = getGuardPath(m, startingPos, currentDirection);
        
        for(Day6Position posToTest: walkedPosition) {
            Position loopPos = checkLoopPossible(m, startingPos, posToTest);
            if(null != loopPos && !loopPositionList.contains(loopPos)) {
                loopPositionList.add(loopPos);
            }

        }
                
        return loopPositionList.size();
    }

    private Position checkLoopPossible(Day6Matrix m, Position startingPos, Day6Position posToTest) {
        Day6Matrix mWithObstable = new Day6Matrix(m);
        Position obstaclePosition = new Position(posToTest);
        obstaclePosition.move(posToTest.getDirection());

        // si c'est un obstacle devant, on tente à droite, c'est une position qu'on ne teste pas sinon
        if( mWithObstable.hasPosition(obstaclePosition) && OBSTACLE == m.get(obstaclePosition)) {
            obstaclePosition= new Position(posToTest);
            obstaclePosition.move(pivot(posToTest.getDirection()));
        }
        
        
        if (!startingPos.equals(obstaclePosition) &&
                mWithObstable.hasPosition(obstaclePosition) && 
                OBSTACLE != m.get(obstaclePosition)) {
            mWithObstable.set(obstaclePosition, '#');
            
            List<Day6Position> foundPath = getGuardPath(mWithObstable, startingPos, UP);
            
            if(null == foundPath) {
                return obstaclePosition;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }



    private List<Day6Position> getGuardPath(Day6Matrix m, Position startingPosition, int[] currentDirection) {
        List<Day6Position> walkedPosition = new ArrayList<>();
        
        walkedPosition.add(new Day6Position(startingPosition, currentDirection));
        
        Position currentPosition = new Position(startingPosition);
        boolean keepGoing = true;
        while(keepGoing) {
            Position nextPosition = getNextPosition(currentPosition, currentDirection);
            if (m.hasPosition(nextPosition)) {
                    if (m.get(nextPosition) == OBSTACLE) {
                        // tourne
                        currentDirection = pivot(currentDirection);
                    } else {
                        if(!walkedPosition.contains(new Day6Position(nextPosition, currentDirection))) {                    
                            addNewPosition(walkedPosition, nextPosition, currentDirection);
                            currentPosition = nextPosition;
                        } else {
                            // loop
                            return null;
                        }
                    }
                
            } else {
                keepGoing = false;
            }            
        }
        
        return walkedPosition;
    }
    
    private void addNewPosition(List<Day6Position> walkedPosition, Position nextPosition,
            int[] currentDirection) {
        walkedPosition.add(new Day6Position(nextPosition, currentDirection));
    }



    private int[] pivot(int[] currentDirection) {
        int[] nextDirection;
        
        if (UP == currentDirection) {
            nextDirection = RIGHT;
        } else if (RIGHT == currentDirection) {
            nextDirection = DOWN;
        } else if (DOWN == currentDirection) {
            nextDirection = LEFT;
        } else {
            nextDirection = UP;
        }
        
        return nextDirection;
    }

    private Position getNextPosition(Position currentPosition, int[] currentDirection) {
        Position nextPosition = new Position(currentPosition);     
        nextPosition.move(currentDirection);
        return nextPosition;
    }

}
