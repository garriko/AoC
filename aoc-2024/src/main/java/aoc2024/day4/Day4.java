package aoc2024.day4;

import java.util.List;

import aoc.shared.model.Matrix;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day4 {

    public Long calculatePart1(Matrix input) {
        Long sum = 0L;
        for(int i =0 ; i< input.getRowCount(); i++) {
            sum += countRowForXMas(input.getMatrix().get(i), i, input);
        }
        return sum;
       
    }
    
    public Long calculatePart2(Matrix input) {
        Long sum = 0L;
        for(int i = 1 ; i< input.getRowCount() - 1; i++) {
            sum += countRowForCrossMas(input.getMatrix().get(i), i, input);
        }
        return sum;
    }
    
    // PART1
    private Long countRowForXMas(List<Character> row, Integer rowIndex, Matrix matrix) {
        Long countForRow=0L;
        for (int j = 0; j < row.size(); j++) {
            Character c = row.get(j);
            if (c == 'X') {
                countForRow += matrix.countForPredicate(rowIndex, j, 4, "XMAS"::equals);
            }
        }

        return countForRow;
    }


    // PART2
    private Long countRowForCrossMas(List<Character> row, Integer rowIndex, Matrix matrix) {
        Long countForRow=0L;
        for (int j = 1; j < row.size() - 1; j++) {
            Character c = row.get(j);
            if (c == 'A') {
                final String diag1 = String.valueOf(matrix.get(rowIndex - 1, j - 1)) + "A" +  String.valueOf(matrix.get(rowIndex + 1, j + 1));
                final String diag2 = String.valueOf(matrix.get(rowIndex + 1, j - 1)) + "A" +  String.valueOf(matrix.get(rowIndex - 1, j + 1));
                if(("SAM".equals(diag1) || "MAS".equals(diag1)) && ("SAM".equals(diag2) || "MAS".equals(diag2))) {
                    countForRow++;
                }
            }
        }

        return countForRow;
    }

}
