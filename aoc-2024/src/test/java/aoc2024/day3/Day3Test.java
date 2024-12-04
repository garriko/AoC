package aoc2024.day3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.test.AocTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day3Test extends AocTest {
    @Inject
    Day3 solver;

    @BeforeEach
    public void init() {
        setInputDir("day3");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        System.out.println("Part 1 : " + solver.calculatePart1(input));
        System.out.println("Part 2 : " + solver.calculatePart2(input));
    }

    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(161, solver.calculatePart1(input));
    }
    
    
    @Test
    public void textExample2() throws IOException {
        List<String> input = readAllLine("example-part2.txt");

        assertEquals(48, solver.calculatePart2(input));
    }
}