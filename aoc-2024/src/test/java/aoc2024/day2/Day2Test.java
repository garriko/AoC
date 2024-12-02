package aoc2024.day2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.test.AocTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day2Test extends AocTest {
    @Inject
    Day2 solver;

    @BeforeEach
    public void init() {
        setInputDir("day2");
    }

    @Test
    public void read() throws IOException {
        List<String> input = readAllLine("input.txt");

        System.out.println("Part 1 : " + solver.calculateSafeReport(input));
        System.out.println("Part 2 : " + solver.calculateSafeReportPart2(input));
    }

    @Test
    public void textExample() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(2, solver.calculateSafeReport(input));
    }
    
    @Test
    public void testAllSafe() throws IOException {
        List<String> input = readAllLine("all-safe.txt");

        assertEquals(8, solver.calculateSafeReportPart2(input));
    }

    @Test
    public void textExample2() throws IOException {
        List<String> input = readAllLine("example.txt");

        assertEquals(4, solver.calculateSafeReportPart2(input));
    }
}