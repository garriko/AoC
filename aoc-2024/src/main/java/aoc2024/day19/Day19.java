package aoc2024.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day19 {

    public long calculatePart1(List<String> input) {

        String[] words = input.get(0).split(",");
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word.trim());
        }

        List<String> patternToCheck = input.subList(2, input.size());

        return patternToCheck.stream().filter(pattern -> canBeConstructed(pattern, trie) > 0).count();
    }

    public long calculatePart2(List<String> input) {

        String[] words = input.get(0).split(",");
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word.trim());
        }

        List<String> patternToCheck = input.subList(2, input.size());
        return patternToCheck.stream().map(pattern -> canBeConstructed(pattern, trie)).reduce(0L, Long::sum);
    }

    private long canBeConstructed(String pattern, Trie trie) {
        return checkSubstringConstruction(pattern, trie, new HashMap<Integer, Long>(), 0);
    }

    // Cache : clé index
    private Long checkSubstringConstruction(String pattern, Trie trie, Map<Integer, Long> cache, int startIndex) {
        if (startIndex == pattern.length()) {
            return 1L;
        }
        
        TrieNode currentNode = trie.getRoot();
        Long subPatternScore = 0L;

        for (int i = startIndex; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (!currentNode.getChildren().containsKey(c)) {
                break;
            }
            
            currentNode = currentNode.getChildren().get(c);

            
            if (currentNode.isEndOfWord()) {                
                if (cache.containsKey(i + 1)) {
                    subPatternScore += cache.get(i + 1);
                } else {
                    Long substringScore = checkSubstringConstruction(pattern, trie, cache, i + 1);

                    if (i + 1 < pattern.length()) {
                        cache.put(i + 1, substringScore);
                    }
                    
                    subPatternScore += substringScore;
                }
                
                
            }
        }

        return subPatternScore;
    }

}
