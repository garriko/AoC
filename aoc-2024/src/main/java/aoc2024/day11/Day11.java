package aoc2024.day11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day11 {

    public Long calculatePart1(String input) {
        List<Stone> stoneList = parserInput(input);

        generation(stoneList, 25);

        return stoneList.stream().map(s -> s.getOccurence()).reduce(0L, Long::sum);
    }

    public Long calculatePart2(String input) {
        List<Stone> stoneList = parserInput(input);

        generation(stoneList, 75);

        return stoneList.stream().map(s -> s.getOccurence()).reduce(0L, Long::sum);
    }
    
    private void generation(List<Stone> stoneList, int occ) {
        if (occ == 0) {
            return;
        }

        for (int i = 0; i < stoneList.size(); i++) {
            Stone currentNumber = stoneList.get(i);
            String currentNumberAsString = String.valueOf(currentNumber.getNumber());

            if (currentNumber.getNumber().equals(0L)) {
                stoneList.get(i).setNumber(1L);
            } else if (currentNumberAsString.length() % 2 == 0) {
                int middle = currentNumberAsString.length() / 2;

                String firstHalf = currentNumberAsString.substring(0, middle);
                String secondHalf = currentNumberAsString.substring(middle);

                stoneList.get(i).setNumber(Long.parseLong(firstHalf));
                stoneList.add(i + 1, new Stone(Long.parseLong(secondHalf), currentNumber.getOccurence()));
                i++;
            } else {
                stoneList.get(i).multiplyNumber();
            }
        }
        
        compressList(stoneList);

        generation(stoneList, --occ);
    }

    

    private void compressList(List<Stone> stoneList) {
        Collections.sort(stoneList);
        
        int i = 0;
        while (i < stoneList.size() - 1) {
            Stone currentStone = stoneList.get(i);
            Stone nextStone = stoneList.get(i + 1);

            // combine si même nombres
            if (currentStone.getNumber().equals(nextStone.getNumber())) {
                currentStone.addOccurence(nextStone.getOccurence());
                
                stoneList.remove(i + 1);
            } else {
                
                i++;
            }
        }
    }
//
//    private void generation(List<Long> stoneList, int occ) {
//        if (occ == 0) {
//            return;
//        }
//
//        for (int i = 0; i < stoneList.size(); i++) {
//            Long currentNumber = stoneList.get(i);
//            String currentNumberAsString = String.valueOf(currentNumber);
//
//            if (currentNumber.equals(0L)) {
//                stoneList.set(i, 1L);
//            } else if (currentNumberAsString.length() % 2 == 0) {
//                int middle = currentNumberAsString.length() / 2;
//
//                String firstHalf = currentNumberAsString.substring(0, middle);
//                String secondHalf = currentNumberAsString.substring(middle);
//
//                stoneList.set(i, Long.parseLong(firstHalf));
//                stoneList.add(i + 1, Long.parseLong(secondHalf));
//                i++;
//            } else {
//                stoneList.set(i, currentNumber * 2024);
//            }
//        }
//
//        generation(stoneList, --occ);
//    }
//
//    private List<Long> parserInput(String input) {
//        List<Long> numberList = new ArrayList<>();
//
//        // Split the string by space and loop through each part
//        String[] numberArray = input.split(" ");
//
//        // Convert each part to an Integer and add to ArrayList
//        for (String num : numberArray) {
//            numberList.add(Long.parseLong(num));
//        }
//
//        return numberList;
//    }
    
    private List<Stone> parserInput(String input) {
        List<Stone> stoneList = new ArrayList<>();

        // Split the string by space and loop through each part
        String[] numberArray = input.split(" ");

        // Convert each part to an Integer and add to ArrayList
        for (String num : numberArray) {
            stoneList.add(new Stone(Long.parseLong(num)));
        }

        return stoneList;
    }

}
