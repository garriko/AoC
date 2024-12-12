package aoc2024.day12;

import java.util.ArrayList;
import java.util.List;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day12 {
    public Long calculatePart1(Day12Matrix m) {
        List<Area> areaList = new ArrayList<Area>();
        for(int i = 0; i < m.getRowCount(); i++) {
            for(int j = 0; j < m.getRowCount(); j++) {
                if(!m.isVisited(i, j)) {
                    areaList.add(buildArea(m, i, j, new Area(m.get(i, j))));
                }
                // sinon déjà visité on passe au suivant
            }
        }
        
        return areaList.stream().map(area -> area.calculCost(m)).reduce(0L, Long::sum);
    }

    private Area buildArea(Day12Matrix m, int row, int col, Area area) {        
        if(m.hasPosition(row, col) && !m.isVisited(row, col) && m.get(row, col).equals(area.getLetter())) {
            m.visit(row, col);
            area.addPosition(new Position(row, col));
            
            // visit quatre direction
            for(int[] direction : Matrix.CARDINAL_DIRECTIONS) {
                buildArea(m, row + direction[0], col + direction[1], area);
            }
            
        }
        
        
        return area;
    }

    public Long calculatePart2(Day12Matrix m) {
        List<Area> areaList = new ArrayList<Area>();
        for(int i = 0; i < m.getRowCount(); i++) {
            for(int j = 0; j < m.getRowCount(); j++) {
                if(!m.isVisited(i, j)) {
                    areaList.add(buildArea(m, i, j, new Area(m.get(i, j))));
                }
                // sinon déjà visité on passe au suivant
            }
        }
        
        return areaList.stream().map(area -> area.calculCostPart2(m)).reduce(0L, Long::sum);
    }
}
