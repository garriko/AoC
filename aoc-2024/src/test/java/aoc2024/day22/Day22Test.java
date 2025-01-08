package aoc2024.day22;

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
public class Day22Test extends AocTest {

    @Inject
    Day22 solver;

    @BeforeEach
    public void init() {
        setInputDir("day22");
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
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(37327623, solver.calculatePart1(input) );
    }
    @Test
    public void textExample123() throws IOException {
        List<String> input = readAllLine("123.txt");

        assertEquals(1110806, solver.calculatePart1(input) );
    }
    @Test
    public void textExamplePart2Other() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(24, solver.calculatePart2(input) );
    }
    
    @Test
    public void textExamplePart2() throws IOException {
        List<String> input = readAllLine("examplePart2.txt");

        assertEquals(23 , solver.calculatePart2(input));
    } 
    

    @Test
    public void textExamplePart21() throws IOException {
        List<String> input = readAllLine("examplePart2-1.txt");

        assertEquals(27 , solver.calculatePart2(input));
    } 

    @Test
    public void textExamplePart22() throws IOException {
        List<String> input = readAllLine("examplePart2-2.txt");

        assertEquals(27 , solver.calculatePart2(input));
    } 
}