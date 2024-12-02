package aoc2024.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day1 {

    /**
     * Pair up the smallest number in the left list with the smallest number in the
     * right list, then the second-smallest left number with the second-smallest
     * right number, and so on.
     * 
     * @param input
     * @return
     */
    public Integer calculateScore(List<String> input) {
        List<Integer> distanceList = getDistanceList(input);
        return distanceList.stream().reduce(0, Integer::sum);
    }

    public Integer calculateSimilarityScore(List<String> input) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        Map<Integer, Integer> cache = new HashMap<>();
        
        input.forEach(line -> {
            var splitted= line.split("   ");
            left.add(Integer.valueOf(splitted[0]));
            right.add(Integer.valueOf(splitted[1]));            
        });
        
       return left.stream().reduce(0, (acc, currentInt) -> {
           if(cache.containsKey(currentInt)) {
               return acc+cache.get(currentInt);
           } 
           
           Integer occ = countOccurence(currentInt, right);
           cache.put(currentInt, currentInt * occ);
           
           return acc + currentInt * occ;
       });
        
     }

    private Integer countOccurence(Integer currentInt, List<Integer> right) {
        return Collections.frequency(right, currentInt);        
    }

    private Integer distance(Integer key, Integer value) {
        return Math.abs(key - value);
    }

    private List<Integer> getDistanceList(List<String> input) {
        List<Integer> distanceList = new ArrayList<>();
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        input.forEach(line -> {
            var splitted = line.split("   ");
            left.add(Integer.valueOf(splitted[0]));
            right.add(Integer.valueOf(splitted[1]));
        });

        Collections.sort(left);
        Collections.sort(right);

        for (int i = 0; i < left.size(); i++) {
            distanceList.add(distance(left.get(i), right.get(i)));
        }

        return distanceList;
    }

}
