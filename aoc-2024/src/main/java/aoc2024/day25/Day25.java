package aoc2024.day25;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day25 {

    public Integer calculatePart1(List<String> input) {
        int currentIndex = 0;
        List<Lock> lockList =new ArrayList<>();
        List<Key> keyList =new ArrayList<>();
        
        int possibleCombinaison = 0;
        
        while(currentIndex < input.size()) {
            if(input.get(currentIndex).charAt(0) == '#') {
                lockList.add(Lock.fromInput(input.subList(currentIndex, currentIndex + 7)));
            }else {
                keyList.add(Key.fromInput(input.subList(currentIndex, currentIndex + 7)));
            }            
            currentIndex += 8;
        }
        
        for(Lock lock : lockList) {
            possibleCombinaison += lock.testKeyList(keyList); 
        }
        
        return possibleCombinaison;
    }

}
