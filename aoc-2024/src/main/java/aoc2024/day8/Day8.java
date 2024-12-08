package aoc2024.day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day8 {
    private Matrix workingMatrix;
    public Long calculatePart1(Matrix input) {
        workingMatrix = input;
        Map<Character, List<Position>> characterPositionMap = mapCharacter(input);

        return characterPositionMap.entrySet().stream()
                .map(this::extractAntinodePosition)
                .flatMap(List::stream)
                .distinct()
                .count();
        
    }
    
    public Long calculatePart2(Matrix input) {
        workingMatrix = input;
        Map<Character, List<Position>> characterPositionMap = mapCharacter(input);
        
        return characterPositionMap.entrySet().stream()
                .map(this::extractAntinodePositionPart2)
                .flatMap(List::stream)
                .distinct()
                .count();
    }
    
    private List<Position> extractAntinodePosition(Entry<Character, List<Position>> characterPositionEntry){        
        List<Position> characterPositionList = characterPositionEntry.getValue();
        
        return getAllSymetricPosition(characterPositionList);
    }
    
    private List<Position> getAllSymetricPosition(List<Position> otherPositionList) {
        List<Position> posList = new ArrayList<Position>();
        
        
        if(otherPositionList.size() > 0) {
            var subList = otherPositionList.subList(1, otherPositionList.size());
            Position currentPosition = otherPositionList.get(0);
            for(Position otherPosition : subList) {
                // calcul coordonnées symétriques
                Position sym1  =new Position(2*currentPosition.getRow() - otherPosition.getRow(), 2*currentPosition.getCol() - otherPosition.getCol());
                Position sym2  =new Position(2*otherPosition.getRow() - currentPosition.getRow(), 2*otherPosition.getCol() - currentPosition.getCol());
                
                if(workingMatrix.hasPosition(sym1)) {
                    posList.add(sym1);
                }
                if(workingMatrix.hasPosition(sym2)) {
                    posList.add(sym2);
                }
            }
            
            posList.addAll(getAllSymetricPosition(subList));
        }
        
        return posList;
    }
    
    private List<Position> extractAntinodePositionPart2(Entry<Character, List<Position>> characterPositionEntry){        
        List<Position> characterPositionList = characterPositionEntry.getValue();
        
        return getAllLinePosition(characterPositionList);
    }
    
    private List<Position> getAllLinePosition(List<Position> otherPositionList) {
        List<Position> posList = new ArrayList<Position>();
        
        
        if(otherPositionList.size() > 0) {
            var subList = otherPositionList.subList(1, otherPositionList.size());
            Position currentPosition = otherPositionList.get(0);
            for(Position otherPosition : subList) {
                int rowDistance = otherPosition.getRow() - currentPosition.getRow();
                int colDistance = otherPosition.getCol() - currentPosition.getCol();
                
                
                // Find the points along the line in both directions
                for (int t = -Math.max(workingMatrix.getRowCount(), workingMatrix.getColCount()); t <= Math.max(workingMatrix.getRowCount(), workingMatrix.getColCount()); t++) {
                    int rowCheck = currentPosition.getRow() + t * rowDistance;
                    int colCheck = currentPosition.getCol() + t * colDistance;

                    if(workingMatrix.hasPosition(rowCheck, colCheck)) {
                        posList.add(new Position(rowCheck, colCheck));
                    }
                }
            }
            
            posList.addAll(getAllLinePosition(subList));
        }
        
        return posList;
    }

    private Map<Character, List<Position>> mapCharacter(Matrix input) {
        Map<Character, List<Position>> m = new HashMap<>();

        for(int i = 0; i < input.getRowCount(); i++) {
            for(int j = 0; j < input.getColCount(); j++) {
                Character c = input.get(i, j);
                
                if('.' != c) {
                    if(m.containsKey(c)) {
                        m.get(c).add(new Position(i,j));
                    } else {
                        m.put(c, new ArrayList<Position>());
                        m.get(c).add(new Position(i,j));
                    }
                }
            }
        }
        
        return m;
    }

    

}
