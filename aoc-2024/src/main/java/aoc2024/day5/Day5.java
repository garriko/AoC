package aoc2024.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day5 {

    public Integer calculatePart2(List<String> input) {
        // number key must be before all number in list
        final Map<Integer, List<Integer>> ruleMap = new HashMap<>();
        Integer middleNumberSum = 0;
        boolean compileRule = true;

        for (String line : input) {
            // compile rules
            if ("".equals(line)) {
                compileRule = false;
                continue;
            }

            if (compileRule) {
                extractRule(ruleMap, line);
            } else {
                List<Integer> lineInteger = Arrays.asList(line.split(",")).stream().map(Integer::valueOf).toList();

                middleNumberSum += getNumberAfterCorrection(lineInteger, ruleMap);
            }
        }

        return middleNumberSum;
    }

    public Integer calculatePart1(List<String> input) {
        // number key must be before all number in list
        final Map<Integer, List<Integer>> ruleMap = new HashMap<>();
        Integer middleNumberSum = 0;
        boolean compileRule = true;

        for (String line : input) {
            // compile rules
            if ("".equals(line)) {
                compileRule = false;
                continue;
            }

            if (compileRule) {
                extractRule(ruleMap, line);
            } else {
                List<Integer> lineInteger = Arrays.asList(line.split(",")).stream().map(Integer::valueOf).toList();
                if (isValidLine(lineInteger, ruleMap)) {
                    middleNumberSum += getMiddleNumber(lineInteger);
                }
            }
        }

        return middleNumberSum;
    }

    private void extractRule(final Map<Integer, List<Integer>> ruleMap, String line) {
        var ruleNumberArray = line.split("\\|");
        Integer key = Integer.valueOf(ruleNumberArray[0]);
        Integer value = Integer.valueOf(ruleNumberArray[1]);
        if (ruleMap.containsKey(key)) {
            ruleMap.get(key).add(value);
        } else {
            List<Integer> list = new ArrayList<>();
            list.add(value);
            ruleMap.put(key, list);
        }
    }

    private Integer getMiddleNumber(List<Integer> lineInteger) {
        return lineInteger.get((lineInteger.size() - 1) / 2);
    }

    private boolean isValidLine(List<Integer> lineInteger, Map<Integer, List<Integer>> ruleMap) {
        boolean isValid = true;

        int i = lineInteger.size() - 1;

        while (i > 0 && isValid) {
            Integer currentValue = lineInteger.get(i);
            List<Integer> currentRules = ruleMap.get(currentValue);

            if (null != currentRules) {
                isValid = Collections.disjoint(currentRules, lineInteger.subList(0, i));
            }

            i--;
        }

        return isValid;
    }

    private Integer getNumberAfterCorrection(List<Integer> lineInteger, Map<Integer, List<Integer>> ruleMap) {
        Integer correctedNumber = 0;

        if (!isValidLine(lineInteger, ruleMap)) {
            List<Integer> correctedLine = correctLine(lineInteger, ruleMap);
            correctedNumber = getMiddleNumber(correctedLine);
        }

        return correctedNumber;
    }

    // Méthode dégueu et pas du tout optimisé mais rien à battre, le tri à bulle c'est cool
    private List<Integer> correctLine(List<Integer> lineInteger, Map<Integer, List<Integer>> ruleMap) {
        List<Integer> correctedLine = copy(lineInteger);
        boolean hasError = true;
        int i = lineInteger.size() - 1;

        while (i > 0 && hasError) {
            Integer currentValue = correctedLine.get(i);
            List<Integer> currentRules = ruleMap.get(currentValue);

            if (null != currentRules) {
                int indexToSwitch = findIndexOfError(correctedLine.subList(0, i), currentRules);
                if (indexToSwitch >= 0) {
                    Collections.swap(correctedLine, i, indexToSwitch);
                    i = correctedLine.size() - 1;
                    
                    if(isValidLine(correctedLine, ruleMap)) {
                        hasError = false;
                    } else {
                        i = correctedLine.size() - 1;
                        continue;
                   }
                }
                
                
            } 

            i--;
        }

        return correctedLine;
    }

    private int findIndexOfError(List<Integer> correctedLine, List<Integer> ruleList) {
        int index = -1;
        for (int i = correctedLine.size() - 1; i >= 0; i--) {
            if (ruleList.contains(correctedLine.get(i))) {
                index = i;
                break;
            }
        }
        return index;
    }

    // mutable
    private List<Integer> copy(List<Integer> a) {
        return a.stream().map(Integer::valueOf).collect(Collectors.toCollection(ArrayList::new));
    }

}
