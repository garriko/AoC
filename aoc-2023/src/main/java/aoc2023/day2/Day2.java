package aoc2023.day2;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day2 {
    private int maxRed;
    private int maxBlue;
    private int maxGreen;

    public Integer solve(List<String> input, int maxRed, int maxBlue, int maxGreen) {
        this.maxBlue = maxBlue;
        this.maxGreen = maxGreen;
        this.maxRed = maxRed;
        
        return input.stream()
        .map(this::mapGame)
        .filter(Game::isGamePossible)
        .map(Game::getGameId)
        .reduce(0, Integer::sum);

    }

    private Game mapGame(String line) {
        Game game = new Game(maxRed, maxBlue, maxGreen);
        Pattern gameIdPattern = Pattern.compile("Game (\\d+):");
        Pattern colorPattern = Pattern.compile("(\\d+)\\s+(blue|red|green)");

        Matcher gameIdMatcher = gameIdPattern.matcher(line);

        int gameId = 0;
        if (gameIdMatcher.find()) {
            gameId = Integer.valueOf(gameIdMatcher.group(1));
        }
        
        game.setGameId(gameId);

        String countsPart = line.split(":")[1].trim();
        String[] colorGroups = countsPart.split(";");

        for (var colorGroup : colorGroups) {
            Handful handful = new Handful();
            Matcher colorMatcher = colorPattern.matcher(colorGroup);

            while (colorMatcher.find()) {
                var number = Integer.valueOf(colorMatcher.group(1));
                var color = colorMatcher.group(2);
                handful.put(color, number);
            }
            
            game.addHandful(handful);
        }

        return game;

    }

    public Integer solvePart2(List<String> input) {       
        return input.stream()
        .map(this::mapGame)
        .map(Game::getGamePower)
        .reduce(0, Integer::sum);
    }

}
