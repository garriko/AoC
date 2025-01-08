package aoc2024.day15;

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
public class Day15Test extends AocTest {

    @Inject
    Day15 solver;

    @BeforeEach
    public void init() {
        setInputDir("day15");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        var debut = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart1(input));
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));

        var debutPart2 = Instant.now();
        System.out.println("Part 1 : " + solver.calculatePart2(input));
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debutPart2));
    }

    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(10092, solver.calculatePart1(input));
    }
    @Test
    public void textExample2() throws IOException {
        List<String> input = readAllLine("example2.txt");

        assertEquals(2028, solver.calculatePart1(input));
    }
    
    @Test
    public void textExampleWork() throws IOException {
        List<String> input = readAllLine("work.txt");
        //assertEquals(9021, solver.calculatePart2(input));
    }
    @Test
    public void textExampleCorner() throws IOException {
        List<String> input = readAllLine("corner.txt");
        assertEquals(1430, solver.calculatePart2(input));
    }

    @Test
    public void textExamplePart2() throws IOException {
        List<String> input = readAllLine("example.txt");
        assertEquals(9021, solver.calculatePart2(input));
    }
    
    @Test
    public void textExample3() throws IOException {
        List<String> input = readAllLine("example3.txt");
        assertEquals(406, solver.calculatePart2(input));
    }
    
    @Test
    public void textExample4() throws IOException {
        List<String> input = readAllLine("example4.txt");
        assertEquals(509, solver.calculatePart2(input));
    }
    @Test
    public void textExample5() throws IOException {
        List<String> input = readAllLine("example5.txt");
        assertEquals(511, solver.calculatePart2(input));
    }
    @Test
    public void textExample6() throws IOException {
        List<String> input = readAllLine("example6.txt");
        assertEquals(816, solver.calculatePart2(input));
    }
    @Test
    public void textExample7() throws IOException {
        List<String> input = readAllLine("example7.txt");
        assertEquals(822, solver.calculatePart2(input));
    }
    
}