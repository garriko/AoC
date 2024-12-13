package aoc2024.day13;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day13 {
    public int calculatePart1(List<String> input) {
        int token = 0;
        int lineCount = 0;
        EquationSolver solver = new EquationSolver();
        for(String line : input) {
            
            
            
            if(lineCount == 0) {
                solver = new EquationSolver();
                solver.parseFirstEquation(line);
                lineCount++;
            } else if(lineCount == 1) {
                solver.parseSecondEquation(line);
                lineCount++;
            } else if(lineCount == 2) {
                solver.parsePrize(line);
                token += solver.solvePart1();
                lineCount++;
            } else {
                lineCount = 0;
            }
            
        }

        return token;
    }
    
    public double calculatePart2(List<String> input) {
        double token = 0;
        int lineCount = 0;
        EquationSolver solver = new EquationSolver();
        for(String line : input) {            
            if(lineCount == 0) {
                solver = new EquationSolver();
                solver.parseFirstEquation(line);
                lineCount++;
            } else if(lineCount == 1) {
                solver.parseSecondEquation(line);
                lineCount++;
            } else if(lineCount == 2) {
                solver.parsePrize(line, true);
                token += solver.solvePart2();
                lineCount++;
            } else {
                lineCount = 0;
            }
            
        }

        return token;
    }

}
