package aoc2024.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day9 {

    public Long calculatePart1(String string) {
        
        
        final List<Space> fileList = parseInput(string);

        final List<Space> sortedList = sortPart1(fileList);
        
        
        return  IntStream.range(0, sortedList.size())
                .boxed()
                .map(index -> (long)( sortedList.get(index).getId() * index))                
                .reduce(0L, Long::sum);
    }
    
    public Long calculatePart2(String input) {
        
        
        final List<Space> fileList = parseInput(input);

        sortPart2(fileList);
        
        
        return  IntStream.range(0, fileList.size())
                .boxed()
                .map(index -> {
                    Space space = fileList.get(index);
                    if (space.isEmpty()) {
                        return 0L;
                    } else {
                        return (long) (fileList.get(index).getId() * index);
                    }
                })                
                .reduce(0L, Long::sum);
    }
    
    private void sortPart2(final List<Space> fileList) {        
        List<Long> checkedIdList = new ArrayList<>();
        
        for(int i = fileList.size()-1 ; i >=0 ; i--) { 
            if(!fileList.get(i).isEmpty() && !checkedIdList.contains(fileList.get(i).getId())) {
                // on tente de ranger cet id
                Long currentId = fileList.get(i).getId();
                List<Integer> idPosition = new ArrayList<Integer>();
                idPosition.add(i);
                int j = i - 1;
                
                while(j >= 0 && currentId.equals(fileList.get(j).getId())) {
                    idPosition.add(j);
                    j--;
                }

                Integer indexToInsert = getIndexToInsert(fileList, j, idPosition.size());

                j=0;
                if (indexToInsert >= 0) {
                    for(int insertingIndex = indexToInsert ; insertingIndex < indexToInsert + idPosition.size(); insertingIndex++) {
                        fileList.set(insertingIndex, fileList.get(i-j));
                        fileList.set(i-j, Space.empty());
                        j++;
                    }
                }
                
                checkedIdList.add(currentId);
            }
        }
        
       
        
    }
    

    /**
     * Vérifie si la liste a de la place pour n occurence entre l'index 0 et maxIndex
     * @param fileList
     * @param i 
     * @param i
     * @return
     */
    private Integer getIndexToInsert(List<Space> fileList, int maxIndex, int occurence) {
        if (fileList == null || fileList.size() < occurence || maxIndex < occurence - 1) {
            return -1;
        }
        
        Integer currentStartIndexChecked = -1;
        int count = 0;  // Start with 1 as the first element is already counted
        for (int i = 0; i <= maxIndex; i++) {
            if (fileList.get(i).isEmpty()) {
                count++;
                if(count == 1) {
                    currentStartIndexChecked = i;
                }
            } else {
                count = 0;  // Reset count if elements are not equal
            }
            // If we find a sequence of n consecutive equal elements, return true
            if (count >= occurence) {
                return currentStartIndexChecked;
            }
        }
        
        return -1;
    }

    private List<Space> sortPart1(final List<Space> fileList) {
        List<Space> sortedList = new ArrayList<Space>();
        
        int currentSortingIndex = fileList.size()-1;
        
        for(int i = 0 ; i < currentSortingIndex ; i++) {
            if(fileList.get(i).isEmpty()) {
                // une place pour le dernier à trier .
                while(fileList.get(currentSortingIndex).isEmpty()) {
                    currentSortingIndex--;
                }
                sortedList.add(fileList.get(currentSortingIndex));
                currentSortingIndex--;
            } else {
                sortedList.add(fileList.get(i));
            }
        }
        
        // chercher le dernier
        while(fileList.get(currentSortingIndex).isEmpty()) {
            currentSortingIndex--;
        }
        sortedList.add(fileList.get(currentSortingIndex));
        return sortedList;
    }

    private List<Space> parseInput(String input) {
        List<Space> parsed = new ArrayList<Space>();
        Long currentId = 0L;
        
        for(int i = 0; i < input.length(); i++) {
            Integer c = Character.getNumericValue(input.charAt(i));
            
            if(i %2 ==1) {
                // empty spaces
                addEmpty(parsed,c);
            }else {
                addFile(parsed,c, currentId);
                currentId++;
            }
        }
        return parsed;
    }

    private void addEmpty(List<Space> parsed, Integer occurence) {
        parsed.addAll(
                Stream.generate(() -> Space.empty())  
                      .limit(occurence)                
                      .collect(Collectors.toList())  
            );
    }
    
    private void addFile(List<Space> parsed, Integer occurence, Long id) {
        parsed.addAll(
                Stream.generate(() -> Space.file(id))  
                      .limit(occurence)                
                      .collect(Collectors.toList())  
            );
    }

}
