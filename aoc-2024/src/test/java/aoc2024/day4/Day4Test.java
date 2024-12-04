package aoc2024.day4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aoc.shared.model.Matrix;
import aoc.shared.test.AocTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class Day4Test extends AocTest {
    @Inject
    Day4 solver;

    @BeforeEach
    public void init() {
        setInputDir("day4");
    }

    @Test
    public void read() throws IOException {
        Matrix input = readFileAsMatrix("input.txt");

        System.out.println("Part 1 : " + solver.calculatePart1(input));
        System.out.println("Part 2 : " + solver.calculatePart2(input));
    }

    @Test
    public void textExample() throws IOException {
        Matrix input  = readFileAsMatrix("example.txt");

        assertEquals(18, solver.calculatePart1(input));
    }
    
    
    @Test
    public void textExample2() throws IOException {
        Matrix input  = readFileAsMatrix("example.txt");

        assertEquals(9, solver.calculatePart2(input));
    }
}
