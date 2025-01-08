package aoc2024.day18;

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
public class Day18Test extends AocTest {

    @Inject
    Day18 solver;

    @BeforeEach
    public void init() {
        setInputDir("day18");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input, 71, 71, 1024));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solver.calculatePart2(input, 71, 71));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    
   
    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(22, solver.calculatePart1(input, 7, 7, 12) );
    }
    
    @Test
    public void textExamplePart2() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals("6,1" , solver.calculatePart2(input, 7, 7));
    }
}
   