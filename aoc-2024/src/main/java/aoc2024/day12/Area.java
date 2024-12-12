package aoc2024.day12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;

public class Area {
    private Character letter;
    private List<Position> positionList;

    public Area(Character character) {
        this.letter = character;
        positionList = new ArrayList<Position>();
    }

    public Character getLetter() {
        return letter;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void addPosition(Position pos) {
        positionList.add(pos);
    }

    @Override
    public String toString() {
        return "Area [letter=" + letter + ", positionList=" + positionList + "]";
    }

    // Périmètre x surface
    // surface = positionList.size()
    // Périmètre : pour chaque position, compter le nombre de voisin avec différents
    // caractères
    public Long calculCost(Day12Matrix m) {
        int surface = positionList.size();
        int perimeter = positionList.stream().map(pos -> {
            int positionPerimeter = 0;
            for (int[] direction : Matrix.CARDINAL_DIRECTIONS) {
                int rowNext = pos.getRow() + direction[0];
                int colNext = pos.getCol() + direction[1];
                if (!m.hasPosition(rowNext, colNext) || !m.get(rowNext, colNext).equals(letter)) {
                    positionPerimeter++;
                }
            }

            return positionPerimeter;
        }).reduce(0, Integer::sum);

        return Long.valueOf(surface * perimeter);
    }

    // nombre de coins de la zone = nombre de côtés
    public Long calculCostPart2(Day12Matrix m) {
        int surface = positionList.size();
        
        Collections.sort(positionList);
        
        int side = 0;
       
        
        for (int i = 0; i < positionList.size(); i++) {
            Position currentPos = positionList.get(i);
            Position posRight = new Position(currentPos); 
            posRight.move(Matrix.RIGHT);
            Position posDown = new Position(currentPos);
            posDown.move(Matrix.DOWN);
            Position posLeft = new Position(currentPos);
            posLeft.move(Matrix.LEFT);
            Position posUp = new Position(currentPos);
            posUp.move(Matrix.UP);
            Position posDownRight = new Position(currentPos);
            posDownRight.move(Matrix.DOWN_RIGHT);
            Position posDownLeft = new Position(currentPos);
            posDownLeft.move(Matrix.DOWN_LEFT);
            Position posUpRight = new Position(currentPos);
            posUpRight.move(Matrix.UP_RIGHT);
            Position posUpLeft = new Position(currentPos);
            posUpLeft.move(Matrix.UP_LEFT);

            if (!m.hasPosition(posLeft) || !m.get(posLeft).equals(letter)) {
                // cas gauche différent
                if (!m.hasPosition(posUp) || !m.get(posUp).equals(letter)) {
                    // CAS
                    // . X .
                    // X A .
                    // . . .
                    side++;
                } else {
                    if (m.hasPosition(posUpLeft) && m.get(posUpLeft).equals(letter)) {
                        // CAS
                        // A A .
                        // X A .
                        // . . .
                        side++;
                    }
                }
            }

            if (!m.hasPosition(posUp) || !m.get(posUp).equals(letter)) {
                // cas haut différent
                if (!m.hasPosition(posLeft) || !m.get(posLeft).equals(letter)) {
                    // CAS
                    // . X .
                    // X A .
                    // . . .
                    side++;
                } else {
                    if (m.hasPosition(posUpLeft) && m.get(posUpLeft).equals(letter)) {
                        // CAS
                        // A X .
                        // X A .
                        // . . .
                        side++;
                    }
                }
            }
            
            if (!m.hasPosition(posRight) || !m.get(posRight).equals(letter)) {
                // cas droite différent
                if (!m.hasPosition(posUp) || !m.get(posUp).equals(letter)) {
                    // CAS
                    // . X .
                    // . A X
                    // . . .
                    side++;
                } else {
                    if (m.hasPosition(posUpRight) && m.get(posUpRight).equals(letter)) {
                        // CAS
                        // . A A
                        // . A X
                        // . . .
                        side++;
                    }
                }
            }
            if (!m.hasPosition(posDown) || !m.get(posDown).equals(letter)) {
                // cas droite différent
                if (!m.hasPosition(posLeft) || !m.get(posLeft).equals(letter)) {
                    // CAS
                    // . . .
                    // X A .
                    // . X .
                    side++;
                } else {
                    if (m.hasPosition(posDownLeft) && m.get(posDownLeft).equals(letter)) {
                        // CAS
                        // . . .
                        // A A .
                        // A X .
                        side++;
                    }
                }
            }
        }
        


        return Long.valueOf(surface * side);
    }

}
