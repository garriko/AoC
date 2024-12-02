package aoc2023.day1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.test.AocTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day1Test extends AocTest {  
    @Inject
    Day1 day1Solver;

    @BeforeEach
    public void init() {
        setInputDir("day1");        
    }

   

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");
        
        System.out.println("Part 1 : " + day1Solver.calculateScore(input));
        System.out.println("Part 2 : " + day1Solver.calculateScorePart2(input));
    }
    
    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("input-example.txt");
        
        assertEquals(142, day1Solver.calculateScore(input));
    }
    
    @Test
    public void textExample2() throws IOException {
        List<String> input = readAllLine("input-example2.txt");
        
        assertEquals(281, day1Solver.calculateScorePart2(input));
    }
}
