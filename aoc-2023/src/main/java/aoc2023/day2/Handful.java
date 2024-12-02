package aoc2023.day2;

import java.util.HashMap;

public class Handful extends HashMap<String, Integer> {
    private static final long serialVersionUID = 1321518247979144465L;

    /**
     * R B G
     */
    public Handful() {
        super();
        put("red", 0);
        put("blue", 0);
        put("green", 0);
    }

    public Integer getBlue() {
        return get("blue");
    }

    public void setBlue(Integer blue) {
        put("blue", blue);
    }

    public Integer getRed() {
        return get("red");
    }

    public void setRed(Integer red) {
        put("red", red);
    }

    public Integer getGreen() {
        return get("green");
    }

    public void setGreen(Integer green) {
        put("green", green);
    }

}
