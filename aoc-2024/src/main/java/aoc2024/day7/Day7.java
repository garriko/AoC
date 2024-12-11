package aoc2024.day7;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day7 {
    
    public Long calculatePart1(List<String> input) {
        return input.stream().map(Line::parseFromString)
                .filter(this::checkLineValidity)
                .map(Line::getTotal)
                .reduce(0L, Long::sum);
    }
    
    public Long calculatePart2(List<String> input) {
        return input.stream().map(Line::parseFromString)
                .filter(this::checkLineValidityPart2)
                .map(Line::getTotal)
                .reduce(0L, Long::sum);
    }

   private boolean checkLineValidity(Line line) {   
        return evaluate(line.getIntegerList(), line.getTotal(), 1, line.getIntegerList().get(0));
        
    }
   private boolean checkLineValidityPart2(Line line) {   
       return evaluatePart2(line.getIntegerList(), line.getTotal(), 1, line.getIntegerList().get(0));
       
   }
    

    private boolean evaluate(List<Long> nums, Long total, Integer index, Long currentValue) { 
        if (index == nums.size()) {
            boolean result = (currentValue.equals(total));
           
            return result;
        }
        
        Long currentNumber = nums.get(index);
        
    
        boolean result = evaluate(nums, total, index + 1, currentValue + currentNumber) ||
                         evaluate(nums, total, index + 1, currentValue * currentNumber);
        
        return result;
    }

    private boolean evaluatePart2(List<Long> nums, Long total, Integer index, Long currentValue) { 
        if (index == nums.size()) {
            boolean result = (currentValue.equals(total));
           
            return result;
        }
        
        Long currentNumber = nums.get(index);
        
    
        boolean result = evaluatePart2(nums, total, index + 1, currentValue + currentNumber) ||
                evaluatePart2(nums, total, index + 1, currentValue * currentNumber) ||
                evaluatePart2(nums, total, index + 1, Long.valueOf(currentValue + "" + currentNumber)) ;
        
        return result;
    }

}
