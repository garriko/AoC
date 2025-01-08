package aoc2024.day25;

import java.util.List;

public class Key extends KeyLock {
    public static Key fromInput(List<String> input) {
        Key key = new Key();        
        key.parse(input.reversed());        
        return key;
    }
}
