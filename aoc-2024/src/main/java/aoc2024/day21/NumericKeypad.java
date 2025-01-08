package aoc2024.day21;

public class NumericKeypad extends Keypad  {
    public NumericKeypad() {
        super(4, 3);
        set(0, 0, '7');
        set(0, 1, '8');
        set(0, 2, '9');
        set(1, 0, '4');
        set(1, 1, '5');
        set(1, 2, '6');
        set(2, 0, '1');
        set(2, 1, '2');
        set(2, 2, '3');
        set(3, 0, OBSTACLE);
        set(3, 1, '0');
        set(3, 2, 'A');
    }
}
