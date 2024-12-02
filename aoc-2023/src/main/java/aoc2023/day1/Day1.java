package aoc2023.day1;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day1 {
    static String[] digitStrings = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    public Long calculateScore(List<String> inputList) {
        return inputList.stream().map(this::calculateLineScore).reduce(0L, Long::sum);
    }

    public Long calculateScorePart2(List<String> inputList) {
        return inputList.stream().map(this::calculateLineScorePart2)
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
    }

    private Long calculateLineScore(String line) {
        String digits = StringUtils.getDigits(line);

        return Long.valueOf(digits.charAt(0) + "" + digits.charAt(digits.length() - 1));
    }

    private String calculateLineScorePart2(String line) {
        final String regex = "(?=(one|two|three|four|five|six|seven|eight|nine|\\d))";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(line);

        // Find the first match
        String firstMatch = null;
        if (matcher.find()) {
            firstMatch = matcher.group(1);

        }

        // Find the last match
        String lastMatch = null;
        while (matcher.find()) {
            lastMatch = matcher.group(1);
        }
        
        if(null == lastMatch) {
            lastMatch = firstMatch;
        }

        return digitCorrespondance(firstMatch) + "" + digitCorrespondance(lastMatch);
    }

    private Long digitCorrespondance(String digit) {
        Long digitInt = 0L;

        switch (digit) {
        case "one":
            digitInt = 1L;
            break;
        case "two":
            digitInt = 2L;
            break;
        case "three":
            digitInt = 3L;
            break;
        case "four":
            digitInt = 4L;
            break;
        case "five":
            digitInt = 5L;
            break;
        case "six":
            digitInt = 6L;
            break;
        case "seven":
            digitInt = 7L;
            break;
        case "eight":
            digitInt = 8L;
            break;
        case "nine":
            digitInt = 9L;
            break;
        default:
            digitInt = Long.valueOf(digit);
            break;
        }

        return digitInt;
    }
}
