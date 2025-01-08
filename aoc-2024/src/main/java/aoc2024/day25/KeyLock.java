package aoc2024.day25;

import java.util.List;

public abstract class KeyLock {
    protected int[] heightArray = new int[5];
    
    protected void parse(List<String> input) {
        for(int i = 1; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                if(line.charAt(j) == '#') {
                    heightArray[j]++;
                }
            }
        }
    }
}
