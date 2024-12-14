package aoc2024.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day14 {

    private int danger;

    public Integer calculatePart1(List<String> input, int maxRow, int maxCol) {
        int centralRow = (maxRow - 1) / 2;
        int centralCol = (maxCol - 1) / 2;
        List<Robot> rList = input.stream().map(line -> Robot.fromLine(line, maxRow, maxCol))
                .peek(r -> r.calculatePositionAfterNIteration(100)).filter(r -> notCentral(r, centralRow, centralCol))
                .toList();

     
        return calculateDanger(rList, maxRow, maxCol);
    }
    
    private int calculateDanger(List<Robot> rList, int maxRow, int maxCol) {
        int centralRow = (maxRow - 1) / 2;
        int centralCol = (maxCol - 1) / 2;
        int topLeftCount = 0;
        int topRightCount = 0;
        int downLeftCount = 0;
        int downRightCount = 0;

        for (Robot r : rList) {
            if (r.getCurrentPosition().getRow() < centralRow && r.getCurrentPosition().getCol() < centralCol) {
                topLeftCount++;
            }
            if (r.getCurrentPosition().getRow() < centralRow && r.getCurrentPosition().getCol() > centralCol) {
                topRightCount++;
            }
            if (r.getCurrentPosition().getRow() > centralRow && r.getCurrentPosition().getCol() < centralCol) {
                downLeftCount++;
            }
            if (r.getCurrentPosition().getRow() > centralRow && r.getCurrentPosition().getCol() > centralCol) {
                downRightCount++;
            }
        }
        return topLeftCount * topRightCount * downLeftCount * downRightCount;
    }

    private boolean notCentral(Robot r, int centralRow, int centralCol) {
        return (r.getCurrentPosition().getRow() != centralRow) && (r.getCurrentPosition().getCol() != centralCol);
    }

    public void calculatePart2(List<String> input, int maxRow, int maxCol) {
        List<Robot> rList = input.stream().map(line -> Robot.fromLine(line, maxRow, maxCol))
                .collect(Collectors.toList());
        int indexLowestDanger = -1;
        int lowestDanger = 0;
        for (int i = 1; i < 10000; i++) {
            for (Robot r : rList) {
                r.calculatePositionAfterNIteration(i);
            }
            
            danger = calculateDanger(rList, maxRow, maxCol);
            if(lowestDanger == 0 || lowestDanger > danger) {
                indexLowestDanger = i;
                lowestDanger = danger;
            }
            
        }
        for(Robot r : rList) {            
            r.calculatePositionAfterNIteration(indexLowestDanger);
        }
        draw(rList.stream().map(Robot::getCurrentPosition).toList(), maxRow, maxCol, indexLowestDanger);
    }

    // le dessin est cassé mais flemme de corriger
    public void draw(List<Position> positionList, int maxRow, int maxCol, int occurence) {
        int currentIndex = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxRow; i++) {
            for (int j = 0; j < maxCol; j++) {
                int lastIndex = findLastIndexForPosition(positionList.subList(currentIndex, positionList.size()), i, j);

                if (lastIndex < 0) {
                    sb.append(".");
                } else {
                    sb.append("X");
                    currentIndex = lastIndex + 1;
                }
            }
            sb.append("\n");
        }

        Path path = Paths.get("robot-" + occurence + ".txt");
        try {
            Files.write(path, sb.toString().getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {            
            e.printStackTrace();
        }
    }

    private int findLastIndexForPosition(List<Position> pList, int searchRow, int searchCol) {

        return pList.lastIndexOf(new Position(searchRow, searchCol));

    }
}
