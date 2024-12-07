package aoc2024.day6;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;

public class Day6Matrix extends Matrix {
    public Day6Matrix(Matrix matrix) {
        super(matrix);
    }
    
    public Position findGuardStartingPosition() {
        // si exception, l'input est faux
        return findFirstPositionFor('^').orElseThrow();
    }


}
