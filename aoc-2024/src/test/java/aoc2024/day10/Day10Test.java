package aoc2024.day10;

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
public class Day10Test extends AocTest {
    @Inject
    Day10 solver;

    @BeforeEach
    public void init() {
        setInputDir("day10");
    }

    @Test
    public void read() throws IOException {
        Matrix input = readFileAsMatrix("input.txt");
        
        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));
        
        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solver.calculatePart2(input));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    @Test
    public void textExample() throws IOException {
        Matrix input = readFileAsMatrix("example.txt");

        assertEquals(36, solver.calculatePart1(input));
    }
   
    @Test
    public void textExample2() throws IOException {
        Matrix input = readFileAsMatrix("example.txt");
        assertEquals(81, solver.calculatePart2(input));
    }
}