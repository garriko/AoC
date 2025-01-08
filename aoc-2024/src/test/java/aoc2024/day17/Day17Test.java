package aoc2024.day17;

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
public class Day17Test extends AocTest {

    @Inject
    Day17 solver;

    @BeforeEach
    public void init() {
        setInputDir("day17");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 2 : " + solver.calculatePart2(input));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    
    @Test
    public void test() throws IOException {
        List<String> input = readAllLine("test.txt");

        assertEquals("0,3,5,4,3,0", solver.calculatePart1(input));
    }
    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals("4,6,3,5,6,3,5,2,1,0", solver.calculatePart1(input));
    }
    
    @Test
    public void textExamplePart2() throws IOException {
        List<String> input = readAllLine("example-part2.txt");

        assertEquals(117440 , solver.calculatePart2(input));
    }
   
}