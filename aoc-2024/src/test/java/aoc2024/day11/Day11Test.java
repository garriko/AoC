package aoc2024.day11;

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
public class Day11Test extends AocTest {

    @Inject
    Day11 solver;

    @BeforeEach
    public void init() {
        setInputDir("day11");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input.get(0)));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solver.calculatePart2(input.get(0)));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(55312, solver.calculatePart1(input.get(0)));
    }

//    @Test
//    public void textExample2() throws IOException {
//        Matrix input = readFileAsMatrix("example.txt");
//        assertEquals(81, solver.calculatePart2(input));
//    }
}
