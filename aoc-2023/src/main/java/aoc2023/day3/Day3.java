package aoc2023.day3;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day3 {
    public Integer solve(List<String> input) {
        int sum = 0;

        for (var i = 0; i < input.size(); i++) {
            if (i == 0) {
                sum += getPartsFromLine(input.get(0), null, input.get(1));
            } else if (i == input.size() - 1) {
                sum += getPartsFromLine(input.get(i), input.get(i - 1), null);
            } else {
                sum += getPartsFromLine(input.get(i), input.get(i - 1), input.get(i + 1));
            }
        }

        return sum;
    }

    public Integer solvePart2(List<String> input) {
        int sum = 0;

        for (var i = 0; i < input.size(); i++) {
            if (i == 0) {
                sum += getPartsFromLinePart2(input.get(0), null, input.get(1));
            } else if (i == input.size() - 1) {
                sum += getPartsFromLinePart2(input.get(i), input.get(i - 1), null);
            } else {
                sum += getPartsFromLinePart2(input.get(i), input.get(i - 1), input.get(i + 1));
            }
        }

        return sum;
    }

    private int getPartsFromLinePart2(String lineToCheck, String previousLine, String nextLine) {
        Pattern pattern = Pattern.compile("\\*");
        Matcher matcher = pattern.matcher(lineToCheck);

        int totalFromLine = 0;
        while (matcher.find()) {
            var gear = isGear(matcher.start(), lineToCheck, previousLine, nextLine);
            if (gear.isGear()) {
                totalFromLine += gear.gearRatio();
            }

        }

        return totalFromLine;
    }

    private GearCheck isGear(int gearIndex, String lineToCheck, String previousLine, String nextLine) {
        GearCheck gear = new GearCheck();
        // check current line
        if (gearIndex > 0 && Character.isDigit(lineToCheck.charAt(gearIndex - 1))) {
            // add number
            Pattern digitPattern = Pattern.compile("\\d+");
            Matcher digitMatcher = digitPattern.matcher(lineToCheck.substring(0, gearIndex));
            String numberFound = null;
            while(digitMatcher.find()) {
                numberFound = digitMatcher.group();
            };
            gear.addNumber(Integer.valueOf(numberFound));
        }

        if (gearIndex < lineToCheck.length() - 1 && Character.isDigit(lineToCheck.charAt(gearIndex + 1))) {
            // add number
            Pattern digitPattern = Pattern.compile("\\d+");
            Matcher digitMatcher = digitPattern.matcher(lineToCheck);
            
            digitMatcher.find(gearIndex+1);
            gear.addNumber(Integer.valueOf(digitMatcher.group()));
        }

        // check previous line
        if (null != previousLine) {
            checkLineForSymbol(gearIndex, previousLine, gear);
        }
        
        // check next line
        if (null != nextLine) {
            checkLineForSymbol(gearIndex, nextLine, gear);
        }

        return gear;
    }

    private void checkLineForSymbol(int gearIndex, String line, GearCheck gear) {
        Pattern digitPattern = Pattern.compile("\\d+");
        Matcher digitMatcher = digitPattern.matcher(line);

        // il faut que le premier chiffre soit assez proche de *
        while (digitMatcher.find() && digitMatcher.start() < gearIndex + 2) {
            int numberSize = digitMatcher.group().length();
            
            if(gearIndex >= digitMatcher.start() - 1 && gearIndex <= digitMatcher.start() + numberSize) {
                gear.addNumber(Integer.valueOf(digitMatcher.group()));
            }
        }
    }

    private Integer getPartsFromLine(String lineToCheck, String previousLine, String nextLine) {
        Pattern digitPatern = Pattern.compile("\\d+");
        Matcher digitMatcher = digitPatern.matcher(lineToCheck);

        int totalFromLine = 0;

        while (digitMatcher.find()) {
            String numberFound = digitMatcher.group();
            if (isNumberEnginePart(digitMatcher.start(), numberFound.length(), lineToCheck, previousLine, nextLine)) {
                totalFromLine += Integer.valueOf(numberFound);
            }
        }

        return totalFromLine;
    }

    private boolean isNumberEnginePart(int startIndex, int numberLength, String lineToCheck, String previousLine,
            String nextLine) {
        
        int neighbourStartIndex = getOtherLineStartIndex(startIndex, lineToCheck.length());
        int neighbourEndIndex = getOtherLineEndIndex(startIndex, numberLength, lineToCheck.length());

        boolean isEnginePart = false;

        // check current line
        if (startIndex > 0 && '.' != lineToCheck.charAt(startIndex - 1)) {
            isEnginePart = true;
        }

        if (!isEnginePart && startIndex + numberLength < lineToCheck.length()
                && '.' != lineToCheck.charAt(startIndex + numberLength)) {
            isEnginePart = true;
        }

        // check previous line
        if (null != previousLine && !isEnginePart) {
            isEnginePart = searchLineForSymbol(previousLine, neighbourStartIndex, neighbourEndIndex);
        }

        // check next line
        if (null != nextLine && !isEnginePart) {
            isEnginePart = searchLineForSymbol(nextLine, neighbourStartIndex, neighbourEndIndex);
        }

        return isEnginePart;
    }

    private boolean searchLineForSymbol(String previousLine, int neighbourStartIndex, int neighbourEndIndex) {
        boolean isEnginePart;
        Pattern noPointPattern = Pattern.compile("[^.]+");
        var toCheck = previousLine.substring(neighbourStartIndex, neighbourEndIndex);
        Matcher symbolMatch = noPointPattern.matcher(toCheck);

        isEnginePart = symbolMatch.find();
        return isEnginePart;
    }

    private int getOtherLineEndIndex(int startIndex, int numberLength, int lineLength) {
        if (startIndex + numberLength == lineLength) {
            return lineLength;
        }

        return startIndex + numberLength + 1;
    }

    private int getOtherLineStartIndex(int startIndex, int lineLength) {
        if (startIndex == 0) {
            return 0;
        }

        return startIndex - 1;
    }

}
