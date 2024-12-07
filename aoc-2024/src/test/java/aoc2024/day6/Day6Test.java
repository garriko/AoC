package aoc2024.day6;

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
public class Day6Test extends AocTest {
    @Inject
    Day6 solver;

    @BeforeEach
    public void init() {
        setInputDir("day6");
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
        Matrix input  = readFileAsMatrix("example.txt");

        assertEquals(41, solver.calculatePart1(input));
    }
   
    @Test
    public void textExample2() throws IOException {
        Matrix input  = readFileAsMatrix("example.txt");

        assertEquals(6, solver.calculatePart2(input));
    }
}