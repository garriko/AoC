package aoc2024.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import aoc.shared.model.Matrix;
import aoc.shared.model.Position;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Day15 {
    private static String DOTS_REGEX = "^(O+)(\\.+)(?=#|O)";
    private static String BIG_BOX_REGEX = "^([\\[\\]]+)(\\.+)(?=#|\\[|\\])";

    public Long calculatePart2(List<String> input) {
        Matrix m = Matrix.fromStringList(input.stream().filter(str -> str.startsWith("#")).map(str -> {
            StringBuilder transformedString = new StringBuilder();

            for (int i = 0; i < str.length(); i++) {
                char currentChar = str.charAt(i);

                switch (currentChar) {
                case '#':
                    transformedString.append("##");
                    break;
                case 'O':
                    transformedString.append("[]");
                    break;
                case '.':
                    transformedString.append("..");
                    break;
                case '@':
                    transformedString.append("@.");
                    break;
                default:
                    // If no transformation is needed, append the character as is
                    transformedString.append(currentChar);
                    break;
                }
            }

            return transformedString.toString();
        }).collect(Collectors.toList()));

        String moveList = String.join("",
                input.stream().filter(
                        str -> str.startsWith("^") || str.startsWith(">") || str.startsWith("<") || str.startsWith("v"))
                        .toList());

        Position start = m.findFirstPositionFor('@').orElseThrow();

        Position currentPos = new Position(start);
        for (int i = 0; i < moveList.length(); i++) {
            char currentMove = moveList.charAt(i);
            m.set(currentPos, '.');

            int[] direction;
            switch (currentMove) {
            case '^':
                direction = Matrix.UP;
                moveRowPart2(m, currentPos, direction[0]);
                break;
            case '>':
                direction = Matrix.RIGHT;
                moveColPart2(m, currentPos, direction[1]);
                break;
            case 'v':
                direction = Matrix.DOWN;
                moveRowPart2(m, currentPos, direction[0]);
                break;
            case '<':
                direction = Matrix.LEFT;
                moveColPart2(m, currentPos, direction[1]);
                break;
            }
            m.set(currentPos, '@');
        }

        return calculateGPS(m, '[');
    }

    public Long calculatePart1(List<String> input) {
        Matrix m = Matrix
                .fromStringList(input.stream().filter(str -> str.startsWith("#")).collect(Collectors.toList()));

        String moveList = String.join("",
                input.stream().filter(
                        str -> str.startsWith("^") || str.startsWith(">") || str.startsWith("<") || str.startsWith("v"))
                        .toList());

        Position start = m.findFirstPositionFor('@').orElseThrow();

        Position currentPos = new Position(start);
        for (int i = 0; i < moveList.length(); i++) {
            char currentMove = moveList.charAt(i);
            m.set(currentPos, '.');

            int[] direction;
            switch (currentMove) {
            case '^':
                direction = Matrix.UP;
                moveRow(m, currentPos, direction[0]);
                break;
            case '>':
                direction = Matrix.RIGHT;
                moveCol(m, currentPos, direction[1]);
                break;
            case 'v':
                direction = Matrix.DOWN;
                moveRow(m, currentPos, direction[0]);
                break;
            case '<':
                direction = Matrix.LEFT;
                moveCol(m, currentPos, direction[1]);
                break;
            }
            m.set(currentPos, '@');
        }

        return calculateGPS(m, 'O');
    }

    private void moveCol(Matrix m, Position currentPosition, int direction) {
        int row = currentPosition.getRow();
        int newCol = currentPosition.getCol();
        newCol += direction;
        if (m.get(row, newCol).equals('.')) {
            currentPosition.setCol(newCol);
        } else if (m.get(row, newCol).equals('O')) {
            if (moveBarrelColDirection(m, direction, row, newCol)) {
                currentPosition.setCol(newCol);
            }
        }
    }

    private void moveRow(Matrix m, Position currentPosition, int direction) {
        int newRow = currentPosition.getRow();
        int col = currentPosition.getCol();
        newRow += direction;

        if (m.hasPosition(newRow, col) && m.get(newRow, col).equals('.')) {
            currentPosition.setRow(newRow);
        } else if (m.get(newRow, col).equals('O')) {
            if (moveBarrelRowDirection(m, direction, newRow, col)) {
                currentPosition.setRow(newRow);
            }
        }
    }

    private void moveColPart2(Matrix m, Position currentPosition, int direction) {
        int row = currentPosition.getRow();
        int newCol = currentPosition.getCol();
        newCol += direction;
        Character currentCharacter = m.get(row, newCol);

        if (currentCharacter.equals('.')) {
            currentPosition.setCol(newCol);
        } else if (currentCharacter.equals('[') || currentCharacter.equals(']')) {
            if (moveBarrelColDirectionPart2(m, direction, row, newCol)) {
                currentPosition.setCol(newCol);
            }
        }
    }

    private void moveRowPart2(Matrix m, Position currentPosition, int direction) {
        int newRow = currentPosition.getRow();
        int col = currentPosition.getCol();
        newRow += direction;
        Character currentCharacter = m.get(newRow, col);

        if (currentCharacter.equals('.')) {
            currentPosition.setRow(newRow);
        } else if (currentCharacter.equals('[')) {
            List<Box> boxList = canMoveRow(m, direction, newRow, col, col + 1);
            if (null != boxList) {
                for (Box box : boxList.reversed()) {
                    m.set(box.getLeftBracket().getRow(), box.getLeftBracket().getCol(), '.');
                    m.set(box.getLeftBracket().getRow(), box.getRightBracket().getCol(), '.');
                    m.set(box.getLeftBracket().getRow() + direction, box.getLeftBracket().getCol(), '[');
                    m.set(box.getLeftBracket().getRow() + direction, box.getRightBracket().getCol(), ']');
                }
                m.set(newRow, col + 1, '.');
                m.set(newRow, col, '.');
                currentPosition.setRow(newRow);
            }

        } else if (currentCharacter.equals(']')) {
            List<Box> boxList = canMoveRow(m, direction, newRow, col - 1, col);
            if (null != boxList) {
                for (Box box : boxList.reversed()) {
                    m.set(box.getLeftBracket().getRow(), box.getLeftBracket().getCol(), '.');
                    m.set(box.getLeftBracket().getRow(), box.getRightBracket().getCol(), '.');
                    m.set(box.getLeftBracket().getRow() + direction, box.getLeftBracket().getCol(), '[');
                    m.set(box.getLeftBracket().getRow() + direction, box.getRightBracket().getCol(), ']');
                }
                currentPosition.setRow(newRow);
            }
        }
    }

    private List<Box> canMoveRow(Matrix m, int direction, int startRow, int colLeftBracket, int colRightBracket) {
        List<Box> boxList = new ArrayList<Box>();
        int nextRow = startRow + direction;

        Character nextRowLeft = m.get(nextRow, colLeftBracket);
        Character nextRowRight = m.get(nextRow, colRightBracket);

        if (nextRowLeft.equals('#') || nextRowRight.equals('#')) {
            return null;
        } else if (nextRowLeft.equals('.') && nextRowRight.equals('.')) {
            Box b = new Box();
            b.setLeftBracket(new Position(startRow, colLeftBracket));
            b.setRightBracket(new Position(startRow, colRightBracket));

            boxList.add(b);

        } else if (nextRowLeft.equals('[')) {
            List<Box> nextRowBoxList = canMoveRow(m, direction, nextRow, colLeftBracket, colRightBracket);
            if (null == nextRowBoxList) {
                boxList = null;
            } else {
                Box b = new Box();
                b.setLeftBracket(new Position(startRow, colLeftBracket));
                b.setRightBracket(new Position(startRow, colRightBracket));
                boxList.add(b);
                boxList.addAll(nextRowBoxList);
            }

        } else if (nextRowLeft.equals(']') && nextRowRight.equals('.')) {
            List<Box> nextRowBoxList = canMoveRow(m, direction, nextRow, colLeftBracket - 1, colLeftBracket);
            if (null == nextRowBoxList) {
                boxList = null;
            } else {
                Box b = new Box();
                b.setLeftBracket(new Position(startRow, colLeftBracket));
                b.setRightBracket(new Position(startRow, colRightBracket));
                boxList.add(b);
                boxList.addAll(nextRowBoxList);
            }
        } else if (nextRowLeft.equals('.') && nextRowRight.equals('[')) {
            List<Box> nextRowBoxList = canMoveRow(m, direction, nextRow, colRightBracket, colRightBracket + 1);

            if (null == nextRowBoxList) {
                boxList = null;
            } else {
                Box b = new Box();
                b.setLeftBracket(new Position(startRow, colLeftBracket));
                b.setRightBracket(new Position(startRow, colRightBracket));
                boxList.add(b);
                boxList.addAll(nextRowBoxList);
            }
        } else {
            List<Box> nextRowBoxListLeft = canMoveRow(m, direction, nextRow, colLeftBracket - 1, colLeftBracket);
            List<Box> nextRowBoxListRight = canMoveRow(m, direction, nextRow, colRightBracket, colRightBracket + 1);

            if (null == nextRowBoxListLeft || null == nextRowBoxListRight) {
                boxList = null;
            } else {
                Box b = new Box();
                b.setLeftBracket(new Position(startRow, colLeftBracket));
                b.setRightBracket(new Position(startRow, colRightBracket));
                boxList.add(b);
                boxList.addAll(nextRowBoxListLeft);
                boxList.addAll(nextRowBoxListRight);
            }
        }

        List<Box> distinctList = checkBoxList(boxList);
        if(null != distinctList) {
            Collections.sort(distinctList);
            if(direction < 0) {
                distinctList = distinctList.reversed();
            
            }
        }
        return distinctList;
    }

    private List<Box> checkBoxList(List<Box> boxList) {
        if (null == boxList) {
            return null;
        }
        return boxList.stream().distinct().collect(Collectors.toList());
    }

    private boolean moveBarrelRowDirection(Matrix m, int direction, int newRow, int col) {
        Pattern pattern = Pattern.compile(DOTS_REGEX);
        Matcher matcher = pattern.matcher(getAllCharInCol(m, newRow, col, direction));

        if (matcher.find()) {
            String Ogroup = matcher.group(1);
            String dotGroup = matcher.group(2);
            int x = Ogroup.length();
            int y = dotGroup.length();

            if (y > 0) {
                m.set(newRow, col, '.');
                m.set(newRow + x * direction, col, 'O');
                return true;
            }

        }
        return false;

    }

    private boolean moveBarrelColDirection(Matrix m, int direction, int row, int startCol) {
        Pattern pattern = Pattern.compile(DOTS_REGEX);
        Matcher matcher = pattern.matcher(getAllCharInRow(m, row, startCol, direction));

        if (matcher.find()) {
            String Ogroup = matcher.group(1);
            String dotGroup = matcher.group(2);
            int x = Ogroup.length();
            int y = dotGroup.length();
            if (y > 0) {

                m.set(row, startCol, '.');
                m.set(row, startCol + x * direction, 'O');
                return true;

            } // sinon pas d'espace

        }
        return false;

    }

    private boolean moveBarrelColDirectionPart2(Matrix m, int direction, int row, int startCol) {
        Pattern pattern = Pattern.compile(BIG_BOX_REGEX);
        Matcher matcher = pattern.matcher(getAllCharInRow(m, row, startCol, direction));

        if (matcher.find()) {
            String boxgroup = matcher.group(1);
            String dotGroup = matcher.group(2);
            int x = boxgroup.length();
            int y = dotGroup.length();
            if (y > 0) {
                m.set(row, startCol, '.');
                for (int i = 1; i < x + 1; i += 2) {
                    if (direction > 0) {
                        m.set(row, startCol + i, '[');
                        m.set(row, startCol + i + 1, ']');
                    } else {
                        m.set(row, startCol - i, ']');
                        m.set(row, startCol - i - 1, '[');
                    }

                }

                return true;

            } // sinon pas d'espace

        }
        return false;
    }

    private List<Box> findBoxToMoveCol(Matrix m, int direction, Position position) {
        List<Box> boxList = new ArrayList<Box>();

        Box currentBox = new Box();
        if (direction > 0) {
            currentBox.setLeftBracket(position);
            Position right = new Position(position);
            right.setCol(right.getCol() + direction);
            currentBox.setRightBracket(right);
        } else {
            currentBox.setRightBracket(position);
            Position left = new Position(position);
            left.setCol(left.getCol() + direction);
            currentBox.setLeftBracket(left);
        }

        return boxList;
    }

    private CharSequence getAllCharInRow(Matrix m, int row, int startCol, int direction) {
        String acc = "";

        if (direction > 0) {
            // down
            for (int i = startCol; i < m.getColCount(); i++) {
                acc += m.get(row, i);
            }
        } else {
            // up
            for (int i = startCol; i >= 0; i--) {
                acc += m.get(row, i);
            }
        }

        return acc;
    }

    private String getAllCharInCol(Matrix m, int newRow, int col, int direction) {
        String acc = "";

        if (direction > 0) {
            // down
            for (int i = newRow; i < m.getRowCount(); i++) {
                acc += m.get(i, col);
            }
        } else {
            // up
            for (int i = newRow; i >= 0; i--) {
                acc += m.get(i, col);
            }
        }

        return acc;
    }

    private Long calculateGPS(Matrix m, char c) {
        Long sum = 0L;

        for (int i = 0; i < m.getRowCount(); i++) {
            for (int j = 0; j < m.getColCount(); j++) {
                if (m.get(i, j).equals(c)) {
                    sum += (100 * i + j);
                }
            }
        }
        return sum;
    }

}
