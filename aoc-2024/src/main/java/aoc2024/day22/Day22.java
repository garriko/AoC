package aoc2024.day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day22 {
    private static final Long PRUNE_VALUE = 16777216L;

    public Long calculatePart1(List<String> input) {
        return input.stream().map(Long::valueOf).map(this::secretNumber).reduce(0L, Long::sum);
    }

    public Integer calculatePart2(List<String> input) {
        List<Map<String, Integer>> bananaPriceList = new ArrayList<>();
        for (String start : input) {
            bananaPriceList.add(toBananaList(Long.valueOf(start)));
        }

        Map<String, Integer> keySumMap = new HashMap<>();

        // Sum the values corresponding to each key across all maps
        for (Map<String, Integer> map : bananaPriceList) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                keySumMap.put(key, keySumMap.getOrDefault(key, 0) + value);
            }
        }

        // Find the key corresponding to the maximum sum
        String maxSumKey = null;
        int maxSum = Integer.MIN_VALUE;

        for (Map.Entry<String, Integer> entry : keySumMap.entrySet()) {
            if (entry.getValue() > maxSum) {
                maxSum = entry.getValue();
                maxSumKey = entry.getKey();
            }
        }

        Log.info("KEY : " + maxSumKey);
        return maxSum;
    }

    private Map<String, Integer> toBananaList(Long input) {
        Map<String, Integer> bananaPriceMap = new HashMap<>();

        List<String> sequence = new ArrayList<>();
        Long secretNumber = input;
        Long previousNumber = secretNumber;
        
        for (int i = 0; i < 2000; i++) {
            secretNumber = nextSecretNumber(secretNumber);

            int previousDigit = getLastDigit(previousNumber);
            int currentDigit = getLastDigit(secretNumber);

            sequence.add(String.valueOf(currentDigit - previousDigit));

            if (sequence.size() == 4) {
                String key = String.join(",", sequence);

                // on ne prend que la première occurence d'une séquence
                if (!bananaPriceMap.containsKey(key)) {
                    bananaPriceMap.put(key, currentDigit);
                }/* else if (bananaPriceMap.get(key) < currentDigit) {
                    bananaPriceMap.put(key, currentDigit);
                }*/
                
                sequence.removeFirst();
            }
            previousNumber = secretNumber;

        }

        return bananaPriceMap;
    }

    private int getLastDigit(Long number) {
        return (int) (number % 10);
    }

    /**
     * @param input
     * @return
     */
    public Long secretNumber(Long input) {
        Long secretNumber = input;

        for (int i = 0; i < 2000; i++) {
            secretNumber = nextSecretNumber(secretNumber);
        }

        return secretNumber;
    }

    public Long nextSecretNumber(final Long secretNumber) {
        Long nextSecretNumber = secretNumber;
        Long mult = nextSecretNumber * 64;
        nextSecretNumber = mult ^ nextSecretNumber;
        nextSecretNumber = nextSecretNumber % PRUNE_VALUE;

        int div = Math.round(nextSecretNumber / 32);
        nextSecretNumber = div ^ nextSecretNumber;
        nextSecretNumber = nextSecretNumber % PRUNE_VALUE;
        Long mult2048 = nextSecretNumber * 2048;

        nextSecretNumber = mult2048 ^ nextSecretNumber;
        nextSecretNumber = nextSecretNumber % PRUNE_VALUE;

        return nextSecretNumber;
    }

}

record Key(int id, String seq) {
}