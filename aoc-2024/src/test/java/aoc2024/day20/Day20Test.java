package aoc2024.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.model.Matrix;
import aoc.shared.test.AocTest;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day20Test extends AocTest {

    @Inject
    Day20 solver;

    @BeforeEach
    public void init() {
        setInputDir("day20");
    }

    @Test
    public void read() throws IOException {
        Matrix input = readFileAsMatrix("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input, 100));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

//        var debutPart2 = Instant.now();
//        System.out.println("Part 2 : " + solver.calculatePart2(input));
//        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    
   
    @Test
    public void textExample() throws IOException {
        Matrix input = readFileAsMatrix("example.txt");

        assertEquals(14, solver.calculatePart1(input, 8) );
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