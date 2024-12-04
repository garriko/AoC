package aoc2024.day3;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day3 {

    private boolean doMultiply = true;

    public Integer calculatePart1(List<String> input) {        
        return input.stream().map(this::scoreForLine).reduce(0, Integer::sum);
    }
    

    public Integer calculatePart2(List<String> input) {
        doMultiply = true;
        return input.stream().map(this::scoreForLineWithDo).reduce(0, Integer::sum);
    }

    private Integer scoreForLine(String line) {
        final String mulRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(mulRegex);
        Matcher matcher = pattern.matcher(line);
        Integer mulSum = 0;
        
        
        while(matcher.find()) {
            mulSum += Integer.valueOf(matcher.group(1))*Integer.valueOf(matcher.group(2));
        }
        
        return mulSum;
    }
    
    private Integer scoreForLineWithDo(String line) {
       
        final String DO= "do()";
        final String DONT= "don't()";
        final String mulRegex = "(?=mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))";
        Pattern pattern = Pattern.compile(mulRegex);
        Matcher matcher = pattern.matcher(line);
        Integer mulSum = 0;
        
        
        while(matcher.find()) {
            if(DO.equals(matcher.group(0))) {
                doMultiply = true;
            } else if (DONT.equals(matcher.group(0))) {
                doMultiply = false;
            } else {
                if(doMultiply)
                mulSum += Integer.valueOf(matcher.group(1))*Integer.valueOf(matcher.group(2));                
            }
            
        }
        
        return mulSum;
    }


}
