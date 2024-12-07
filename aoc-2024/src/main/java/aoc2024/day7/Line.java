package aoc2024.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {
    private Long total;
    private List<Long> integerList = new ArrayList<>();

    public static Line parseFromString(String input) {
        Line line =  new Line();

        var parts = input.split(":");
        line.total = Long.valueOf(parts[0]);
        line.integerList = Arrays.asList(parts[1].trim().split(" "))
                .stream()
                .map(Long::valueOf)
                .toList();
        
        return line;
    }

    public Long getTotal() {
        return total;
    }

    public List<Long> getIntegerList() {
        return integerList;
    }
}
