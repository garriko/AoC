package aoc2023.day2;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int maxRed;
    private int maxBlue;
    private int maxGreen;
    private int neededRed = 0;
    private int neededBlue = 0;
    private int neededGreen = 0;
    private List<Handful> handfulList;
    private int gameId;
    private boolean isGamePossible;
    
    
    public Game(int maxRed, int maxBlue, int maxGreen) {
        handfulList = new ArrayList<>();
        this.maxRed = maxRed;
        this.maxBlue = maxBlue;
        this.maxGreen = maxGreen;
        isGamePossible = true;        
    }
    public List<Handful> getHandfulList() {
        return handfulList;
    }
    public void setHandfulList(List<Handful> handfulList) {
        this.handfulList = handfulList;
    }
    public int getGameId() {
        return gameId;
    }
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public boolean isGamePossible() {
        return isGamePossible;
    }
    public void setGamePossible(boolean isGamePossible) {
        this.isGamePossible = isGamePossible;
    }
    
    public int getGamePower() {
        return neededBlue * neededGreen * neededRed;
    }
    
    public void addHandful(Handful handful) {
        var red = handful.getRed();        
        Integer blue = handful.getBlue();
        
        Integer green = handful.getGreen();
        if(red > maxRed || blue > maxBlue || green > maxGreen) {
            isGamePossible = false;
        }
        
        if(red > neededRed ) {
            neededRed = red;
        }
        if(blue > neededBlue) {
            neededBlue = blue;
        }
        if(green > neededGreen ) {
            neededGreen = green;
        }
        
        this.handfulList.add(handful);
    }
    
    
}
