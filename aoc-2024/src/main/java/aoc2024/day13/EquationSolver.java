package aoc2024.day13;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * x*A + y*B = C <br/>
 * x*D + y*E = F <br/>
 * x*3+y doit être le plus petit possible <br/>
 * x et y <= 100
 */
public class EquationSolver {
    private static final Long RESULT_CORRECTION = 10000000000000L;
    private static final String COEFF_REGEX = "X\\+(\\d+), Y\\+(\\d+)";
    private static final String DESTINATION_REGEX = "X=(\\d+), Y=(\\d+)";

    private Long a;
    private Long b;
    private Long c;
    private Long d;
    private Long e;
    private Long f;

   

    public Long getA() {
        return a;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public Long getB() {
        return b;
    }

    public void setB(Long b) {
        this.b = b;
    }

    public Long getC() {
        return c;
    }

    public void setC(Long c) {
        this.c = c;
    }

    public Long getD() {
        return d;
    }

    public void setD(Long d) {
        this.d = d;
    }

    public Long getE() {
        return e;
    }

    public void setE(Long e) {
        this.e = e;
    }

    public Long getF() {
        return f;
    }

    public void setF(Long f) {
        this.f = f;
    }

    public void parseFirstEquation(String line) {
        Pattern pattern = Pattern.compile(COEFF_REGEX);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            a = Long.valueOf(matcher.group(1));
            d = Long.valueOf(matcher.group(2));
        }
    }

    public void parseSecondEquation(String line) {
        Pattern pattern = Pattern.compile(COEFF_REGEX);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            b = Long.valueOf(matcher.group(1));
            e = Long.valueOf(matcher.group(2));
        }
    }
    
    public void parsePrize(String line) {
        parsePrize(line, false);

    }

    public void parsePrize(String line, boolean part2) {
        Pattern pattern = Pattern.compile(DESTINATION_REGEX);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            c = Long.valueOf(matcher.group(1));
            f = Long.valueOf(matcher.group(2));
        }
        
        if(part2) {
            c += RESULT_CORRECTION;
            f += RESULT_CORRECTION;
        }
    }

    public int solvePart1() {
        Long deter = a * e - b * d;
        int token = 0;
        if (deter != 0) {

            // x
            double buttonAPushExact = (double) (c * e - b * f) / deter;
            // y
            double buttonBPushExact = (double) (a * f - c * d) / deter;

            if (isNaturalNumber(buttonAPushExact) && isNaturalNumber(buttonBPushExact)
                    && buttonAPushExact <= 100 && buttonBPushExact <= 100) {
                token = (int) (buttonAPushExact * 3 + buttonBPushExact);
            }
        }

        return token;

    }
    
    public double solvePart2() {
        Long deter = a * e - b * d;
        double token = 0;
        if (deter != 0) {

            // x
            double buttonAPushExact = (double) (c * e - b * f) / deter;
            // y
            double buttonBPushExact = (double) (a * f - c * d) / deter;

            if (isNaturalNumber(buttonAPushExact) && isNaturalNumber(buttonBPushExact)) {
                token =  buttonAPushExact * 3 + buttonBPushExact;
            }
        }

        return token;

    }

    boolean isNaturalNumber(double value) {
        return value >= 0 && value == Math.floor(value);
    }
}
