package aoc2024.day12;

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
public class Day12Test extends AocTest {

    @Inject
    Day12 solver;

    @BeforeEach
    public void init() {
        setInputDir("day12");
    }

    @Test
    public void read() throws IOException {
        Matrix input = readFileAsMatrix("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(new Day12Matrix(input)));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solver.calculatePart2(new Day12Matrix(input)));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    @Test
    public void textExample() throws IOException {
        Matrix input = readFileAsMatrix("example.txt");

        assertEquals(1930, solver.calculatePart1(new Day12Matrix(input)));
    }
    
    @Test
    public void textExampleWithin() throws IOException {
        Matrix input = readFileAsMatrix("example-within.txt");

        assertEquals(772, solver.calculatePart1(new Day12Matrix(input)));
    }

    @Test
    public void textExample2() throws IOException {
        Matrix input = readFileAsMatrix("example.txt");
        assertEquals(1206, solver.calculatePart2(new Day12Matrix(input)));
    }
    @Test
    public void textExampleWithin2() throws IOException {
        Matrix input = readFileAsMatrix("example-within.txt");
        assertEquals(436, solver.calculatePart2(new Day12Matrix(input)));
    }
}
