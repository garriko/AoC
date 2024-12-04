package aoc.shared.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import aoc.shared.model.Matrix;

public class AocTest {
    protected Path workingDir;
    
    protected void setInputDir(String dir) {
        workingDir = Path.of("", "src/test/resources/" + dir );              
    }
    
    
    protected List<String> readAllLine(String filename) throws IOException {
        Path file = workingDir.resolve(filename);  
        List<String> input = Files.readAllLines(file);
        return input;
    }
    
    protected Matrix readFileAsMatrix(String filename) throws IOException {
        Path file = workingDir.resolve(filename);  
        
        return Matrix.fromFile(file);
    }
}
