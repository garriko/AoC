package aoc2024.day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day17 {
    private static final String DEC_REGEX = "(\\d+)";
    
    public Long calculatePart2(List<String> input) {
        
        Long registerB = parseRegister(input.get(1));
        Long registerC = parseRegister(input.get(2));

        List<Integer> instructionList = parseInstruction(input.get(4));
        List<Integer> remainingProgram = new ArrayList<>(instructionList);
        List<Integer> program = new ArrayList<>();
        long registerA = 0L;
        
        
        
        while (!remainingProgram.isEmpty()) {
            registerA--;
            // on commence par la fin
            program.addFirst(remainingProgram.removeLast());
            String pString = program.stream().map(Long::toString).collect(Collectors.joining(","));
            Computer computer;
            
            String output;
            do {              
                registerA++;
                computer = new Computer(registerA, registerB, registerC);                
                output = processInstructionList(computer, instructionList, true, program);
            } while (!pString.equals(output));
            if (!remainingProgram.isEmpty()) {
                registerA = registerA << 3;
            }
        }
        

        return registerA;

    }

    public String calculatePart1(List<String> input) {
        Long registerA = parseRegister(input.get(0));
        Long registerB = parseRegister(input.get(1));
        Long registerC = parseRegister(input.get(2));

        List<Integer> instructionList = parseInstruction(input.get(4));

        Computer c = new Computer(registerA, registerB, registerC);

        return processInstructionList(c, instructionList);

    }
    
    private String processInstructionList(Computer c, List<Integer> instructionList) {
        return processInstructionList(c, instructionList, false, null);
    }

    private String processInstructionList(Computer c, List<Integer> instructionList, boolean part2, List<Integer> expected) {
        List<Integer> output = new ArrayList<Integer>();
        
        int instructionPointer = 0;
        while (instructionPointer < instructionList.size() - 1) {
            int opCode = instructionList.get(instructionPointer);
            int operand = instructionList.get(instructionPointer + 1);
            
            switch(opCode) {
            case 0:
                c.adv(operand);
                instructionPointer += 2;
                break;
            case 1:
                c.bxl(operand);
                instructionPointer += 2;
                break;
            case 2:
                c.bst(operand);
                instructionPointer += 2;
                break;
            case 3:
                instructionPointer = c.jnz(operand, instructionPointer);
                break;
            case 4:
                c.bxc(operand);
                instructionPointer += 2;
                break;
            case 5:
                output.add(c.out(operand));
                if(part2 && !sameListInteger(output, expected)) {
                    return null;
                }
                instructionPointer += 2;
                break;
            case 6:
                c.bdv(operand);
                instructionPointer += 2;
                break;
            case 7:
                c.cdv(operand);
                instructionPointer += 2;
                break;
            }
        }
        
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    private boolean sameListInteger(List<Integer> output, List<Integer> expected) {
        if(output.size() > expected.size()) {
            return false;
        }
        
        for(int i=0; i<output.size(); i++) {
            if(!output.get(i).equals(expected.get(i))) {
                return false;
            }
        }
        return true;
    }

    private Long parseRegister(String line) {
        long register = 0;
        Pattern pattern = Pattern.compile(DEC_REGEX);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            register = Long.valueOf(matcher.group(1));
        }

        return register;
    }

    private List<Integer> parseInstruction(String line) {
        return Arrays.asList(line.replace("Program: ", "").split(",")).stream().map(Integer::valueOf).toList();
    }

}
