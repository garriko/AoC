package aoc2024.day21;

public class DirectionalKeypad extends Keypad  {
    
    public DirectionalKeypad() {
        super(2, 3);
        set(0, 0, OBSTACLE);
        set(0, 1, '^');
        set(0, 2, 'A');
        set(1, 0, '<');
        set(1, 1, 'v');
        set(1, 2, '>');
    }
    
   

}
