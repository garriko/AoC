package aoc2024.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day24 {
    private static final String PATTERN_INIT = "(.\\d+): (\\d+)";
    private static final String PATTERN_GATE = "(..{2}) ([A-Z]{2,3}) (..{2}) -> (..{2})";

    public Long calculatePart1(List<String> input) {
        Map<String, Wire> wireMap = parseInput(input);
        
        int hasValueNumber = 0;
        
        do {
            hasValueNumber = 0;
            for(Wire wire : wireMap.values()) {
                if(!wire.hasValue()) {
                    wire.computeValue(wireMap);
                    if(wire.hasValue()) {
                        hasValueNumber++;
                    }
                } else {
                    hasValueNumber++;
                }
            }
        } while(hasValueNumber != wireMap.size());

        return extractResult(wireMap);
    }

    private Long extractResult(Map<String, Wire> wireMap) {
        List<Boolean> booleanList = wireMap.values()
        .stream()       
        .filter(wire -> wire.getName().startsWith("z"))
        .sorted()
        .map(Wire::getValue)
        .toList();
        
        return LongStream.range(0, booleanList.size())                
                .filter(i -> booleanList.get((int) i)) 
                .map(i -> (long) Math.pow(2, i)) 
                .reduce(0L, Long::sum); // Sum all the values
    }

    private Map<String, Wire> parseInput(List<String> input) {
        Map<String, Wire> wireMap = new HashMap<>();

        Pattern patternInit = Pattern.compile(PATTERN_INIT);
        Pattern patternGate = Pattern.compile(PATTERN_GATE);
        for (String line : input) {
            var mInit = patternInit.matcher(line);
            var mGate = patternGate.matcher(line);

            if(mInit.find()) {
                Integer intValue = Integer.valueOf(mInit.group(2));
                String wireName = mInit.group(1);
                Wire wire = new Wire(wireName, intValue == 1);
                wireMap.put(wireName, wire);
            } else if (mGate.find()) {        
                String wireName = mGate.group(4);
                Wire wire = new Wire(wireName, mGate.group(1), mGate.group(3), mGate.group(2));
                wireMap.put(wireName, wire);
            }
        }
        
        return wireMap;
    }

    // Wikipedia : ripple-carry adder
    public String calculatePart2(List<String> input) {
        List<String> faultyConnection = new ArrayList<>();
        Map<String, Wire> wireMap = parseInput(input);
        
        
        for(Wire wire: wireMap.values()) {
            if(wire.hasValue()) {
                continue;
            }
            //  If the output of a gate is z, then the operation has to be XOR unless it is the last bit
            if(wire.getName().startsWith("z") && !wire.getOperation().equals("XOR") && !wire.getName().equals("z45")) {
                faultyConnection.add(wire.getName());
            } 
            // If the output of a gate is not z and the inputs are not x, y then it has to be AND / OR, but not XOR.
            else if(!wire.getName().startsWith("z") &&
                    !wire.getOperandeLeft().startsWith("x") &&
                    !wire.getOperandeRight().startsWith("x") &&
                    !wire.getOperandeLeft().startsWith("y") &&
                    !wire.getOperandeRight().startsWith("y") && 
                    wire.getOperation().equals("XOR")
                     ) {
                faultyConnection.add(wire.getName());
            }
            // If a XOR gate with inputs x, y, there must be another XOR gate with this gate as an input.
            // if it does not exist, XOR gate is wrong.
            else if (wire.getOperation().equals("XOR") && 
                    (
                     (wire.getOperandeLeft().startsWith("x") && wire.getOperandeRight().startsWith("y")) || 
                     (wire.getOperandeRight().startsWith("x") && wire.getOperandeLeft().startsWith("y"))
                    ) &&
                    !"x00".equals(wire.getOperandeLeft()) &&
                    !"x00".equals(wire.getOperandeRight()) &&
                    !"y00".equals(wire.getOperandeLeft()) &&
                    !"y00".equals(wire.getOperandeRight()) 
                    ) {
                if(!searchForGateWithInput(wireMap, wire.getName(), "XOR")) {
                    faultyConnection.add(wire.getName());
                }
            }
            // if AND-gate, there must be an OR-gate with this gate as an input.
            // If that gate doesn't exist, the original AND gate is faulty.
            else if (wire.getOperation().equals("AND") && 
                    ((wire.getOperandeLeft().startsWith("x") && wire.getOperandeRight().startsWith("y")) || 
                     (wire.getOperandeRight().startsWith("x") && wire.getOperandeLeft().startsWith("y"))
                    )
                    &&
                    !"x00".equals(wire.getOperandeLeft()) &&
                    !"x00".equals(wire.getOperandeRight()) &&
                    !"y00".equals(wire.getOperandeLeft()) &&
                    !"y00".equals(wire.getOperandeRight()) ) {
                if(!searchForGateWithInput(wireMap, wire.getName(), "OR")) {
                    faultyConnection.add(wire.getName());
                }
            }
            
        }
        
        return faultyConnection.stream().sorted().collect(Collectors.joining(","));
    }

    private boolean searchForGateWithInput(Map<String, Wire> wireMap, String searchedName, String operation) {
        boolean found = false;
        for(Wire wire: wireMap.values()) {
            
            found = (searchedName.equals(wire.getOperandeLeft()) || searchedName.equals(wire.getOperandeRight())) 
                    && operation.equals(wire.getOperation());
            
            if(found) {
                break;
            }
        }
        return found;
    }

}
