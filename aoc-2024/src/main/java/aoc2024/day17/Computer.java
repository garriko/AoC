package aoc2024.day17;

public class Computer {
    private Long registerA;
    private Long registerB;
    private Long registerC;

    public Computer(Long registerA, Long registerB, Long registerC) {
        super();
        this.registerA = registerA;
        this.registerB = registerB;
        this.registerC = registerC;
    }

    public void adv(int operand) {
        Long div = registerA / (long) Math.pow(2, combo(operand));
        registerA = div;
    }
    
    public void bdv(int operand) {
        long div = registerA / (long) Math.pow(2, combo(operand));
        registerB = div;
    }
    
    public void cdv(int operand) {
        long div = registerA / (long) Math.pow(2, combo(operand));
        registerC = div;
    }

    public void bxl(int operand) {
        registerB = registerB ^ operand;
    }
    
    public void bst(int operand) {
        registerB = combo(operand) % 8;
    }

    /**
     * return new instruction pointer
     * @param operand
     * @return
     */
    public int jnz(int operand, int instructionPointer) {
        if(registerA != 0) {
            return operand;
        } else {
            return instructionPointer + 2;
        }    
    }
    
    public void bxc(int operand) {
        registerB = registerB ^ registerC;
    }
    
    public int out(int operand) {
        return (int) combo(operand) % 8;
    }
    
    

    private long combo(int operand) {
        long combo = 0;
        switch (operand) {
        case 0, 1 , 2, 3:
            combo = operand;
            break;
        case 4:
            combo = registerA;
            break;
        case 5:
            combo = registerB;
            break;
        case 6:
            combo = registerC;
            break;
        case 7:
            throw new RuntimeException();
        }
        return combo;
    }
}
