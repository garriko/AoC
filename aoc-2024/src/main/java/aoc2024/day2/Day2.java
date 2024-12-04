package aoc2024.day2;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day2 {

    public Integer calculateSafeReport(List<String> input) {
        var debut = Instant.now();
        int numberOfSafeReport = 0;

        for (var report : input) {
            var levelList = Arrays.asList(report.split(" ")).stream().map(Integer::valueOf)
                    .collect(Collectors.toList());

            if (isReportSafe(levelList, false)) {
                numberOfSafeReport++;
            }
        }
        
        Log.info("Part 1 time : " + Duration.between(Instant.now(), debut));
        return numberOfSafeReport;
    }

    public Integer calculateSafeReportPart2(List<String> input) {
        var debut = Instant.now();
        int numberOfSafeReport = 0;

        for (var report : input) {
            var levelList = Arrays.asList(report.split(" ")).stream().map(Integer::valueOf)
                    .collect(Collectors.toList());

            if (isReportSafe(levelList, true)) {
                numberOfSafeReport++;
            }
        }
        
        Log.info("Part 2 time : " + Duration.between(Instant.now(), debut));
        
        return numberOfSafeReport;
    }

    private boolean isReportSafe(List<Integer> levelList, boolean canRemoveLevel) {
        boolean isAscending = levelList.get(0) <= levelList.get(1);
        boolean safe = true;

        for (var i = 0; i < levelList.size() - 1; i++) {
            int current = levelList.get(i);
            int next = levelList.get(i + 1);

            if ((current > next && !isAscending) || (current < next && isAscending)) {
                // check distance
                int distance = Math.abs(current - next);
                if (distance > 3) {
                    safe = false;
                }
            } else {
                safe = false;
            }

            if (!safe) {
                if (canRemoveLevel) {
                    List<Integer> removedPreviousLevelList = null;
                    if(i > 0) {
                    removedPreviousLevelList = Stream.concat(levelList.subList(0, i - 1).stream(),
                            levelList.subList(i, levelList.size()).stream()).toList();
                    }
                    
                    var removedCurrentLevelList = Stream.concat(levelList.subList(0, i).stream(),
                            levelList.subList(i + 1, levelList.size()).stream()).toList();

                    List<Integer> removedNextLevelList = Stream.concat(levelList.subList(0, i + 1).stream(),
                            levelList.subList(i + 2, levelList.size()).stream()).toList();

                    boolean removePreviousSafe = null != removedPreviousLevelList ? isReportSafe(removedPreviousLevelList, false) : false;
                   
                    if(!removePreviousSafe) {
                        boolean removeCurrentSafe = isReportSafe(removedCurrentLevelList, false);
                        if (!removeCurrentSafe) {
                            safe = isReportSafe(removedNextLevelList, false);
                        } else {
                            safe = removeCurrentSafe;
                        }
                    } else {
                        safe = removePreviousSafe;
                    }


                    if(safe) {
                        break;
                    }
                }
            }
        }

        return safe;
    }

}
