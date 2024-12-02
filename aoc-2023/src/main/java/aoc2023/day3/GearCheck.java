package aoc2023.day3;

public class GearCheck {
    private int firstNumber = 0;
    private int secondNumber = 0;
    private boolean tooMuchNumbers = false;
    
    public GearCheck() {        
    }
    
    public void addNumber(int number) {
        if(firstNumber == 0) {
            firstNumber = number;
        } else if (secondNumber ==0)  {
            secondNumber = number;
        } else {
            tooMuchNumbers = true;
        }
    }
    
    public boolean isGear() {
        return !tooMuchNumbers && firstNumber != 0 && secondNumber != 0;
    }
  
    public int gearRatio() {
        return firstNumber * secondNumber;
    }
    
    
}
