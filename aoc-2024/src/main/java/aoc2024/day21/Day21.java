package aoc2024.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day21 {
    private Map<String, String> directionalCache;
  
    
    public Long solve(List<String> input, int directionalKeypadNumber) {
        Keypad.times = 0;
        directionalCache = new HashMap<>();
        
        Long compl = input.parallelStream()
            .map(code -> {
                String shortestSequence = findShortestSequence(code, "A");
                String sequence = shortestSequence;
                int level = 0;
                while (level < directionalKeypadNumber) {
                    sequence = findHumanPath(sequence);
                    level++;
                }
                
                return calculComplexity(code, sequence.length());
            }).reduce(0L, Long::sum);        
        
        return compl;
    }
    

    
    private String findShortestSequence(String code, String currentPosition) {
        if (code.isEmpty()) {
            return "";
        }

        String nextChar = String.valueOf(code.charAt(0));

        return computeKeypadPath(currentPosition, nextChar) + findShortestSequence(code.substring(1), nextChar);

    }

    private String computeKeypadPath(String currentPosition, String nextChar) {
        String path = "";
        NumericKeypad keypad = new NumericKeypad();
        path = keypad.findShortestPath(currentPosition.charAt(0), nextChar.charAt(0)) + "A";

        return path;
    }

    private String findHumanPath(String code) {
        if (code.isEmpty()) {
            return "";
        }
        
        StringBuilder path = new StringBuilder();
        String previousCharacter = "A";
        StringBuilder group = new StringBuilder();


        for(int i = 0; i < code.length(); i++) {        
            if (group.length() == 0 || code.charAt(i) == group.charAt(group.length() - 1)) {
                group.append(code.charAt(i));  // Add current character to the group
            } else {
                String nextChar = String.valueOf(group.charAt(0));
                path.append(shortestPath(previousCharacter, nextChar));
                path.append("A".repeat(group.length() - 1));
                
                previousCharacter = nextChar;
                group.setLength(0);  // Reset the group
                group.append(code.charAt(i));  // Start a new group with the current character
            }
        }      
        String nextChar = String.valueOf(group.charAt(0));
        path.append(shortestPath(previousCharacter, nextChar));
        path.append(String.valueOf("A").repeat(group.length() - 1));
        
        return path.toString();
    }
    
    private String shortestPath(String position, String next) {
        String shortest = null;
        if (directionalCache.containsKey(position + next)) {           
            shortest = directionalCache.get(position + next);
        } else {            
            DirectionalKeypad keypad = new DirectionalKeypad();
            StringBuilder shortestBuilder = new StringBuilder(keypad.findShortestPath(position.charAt(0), next.charAt(0)));
            shortestBuilder.append("A");
            shortest =  shortestBuilder.toString();

            directionalCache.put(position + next, shortest);
        }
        
        return shortest;
    }

    private Long calculComplexity(String code, int shortestSequenceLength) {
        return Long.valueOf(code.substring(0, 3)) * shortestSequenceLength;
    }
}
