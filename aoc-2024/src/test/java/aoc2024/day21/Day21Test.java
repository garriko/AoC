package aoc2024.day21;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.test.AocTest;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day21Test extends AocTest {

    @Inject
    Day21 solver;
    @Inject
    Day21Part2 solverpart2;

    @BeforeEach
    public void init() {
        setInputDir("day21");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

//        var debut = Instant.now();
//        System.out.println("Part 1 : " + solver.solve(input, 2));
//        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solverpart2.solve(input, 25));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    
   
    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(126384, solver.solve(input, 2) );
    }
    
    @Test
    public void textCase() throws IOException {
        List<String> input = readAllLine("testcase.txt");

        assertEquals(26250, solver.solve(input, 25) );
    }
    
//    @Test
//    public void textExamplePart2() throws IOException {
//        List<String> input = readAllLine("example.txt");
//
//        assertEquals(16 , solver.calculatePart2(input));
//    } 
//    
//    @Test
//    public void textExampletest() throws IOException {
//        List<String> input = readAllLine("test.txt");
//
//        assertEquals(2 , solver.calculatePart2(input));
//    }
}