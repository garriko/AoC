package aoc2024.day24;

import java.util.Map;

public class Wire implements Comparable<Wire> {
    private String name;
    private Boolean value = null;
    private String operandeLeft;
    private String operandeRight;
    private String operation;

    public Wire(String name, boolean value) {
        this.value = value;
        this.name = name;
    }

    public Wire(String name, String operandeLeft, String operandeRight, String operation) {
        this.name = name;
        this.operandeLeft = operandeLeft;
        this.operandeRight = operandeRight;
        this.operation = operation;
    }

    public boolean hasValue() {
        return null != value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
    

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Wire [name=" + name + ", value=" + value + "]";
    }

    public void computeValue(Map<String, Wire> wireMap) {
        Wire leftWire = wireMap.get(operandeLeft);
        Wire rightWire = wireMap.get(operandeRight);

        if (leftWire.hasValue() && rightWire.hasValue()) {
            doComputeValue(leftWire.getValue(), rightWire.getValue());
        }
    }

    private void doComputeValue(Boolean v1, Boolean v2) {
        switch (operation) {
        case "AND": {
            value = v1 && v2;
            break;
        }
        case "XOR": {
            value = v1 ^ v2;
            break;
        }
        case "OR": {
            value = v1 || v2;
            break;
        }
        }

    }

    @Override
    public int compareTo(Wire o) {        
        return name.compareTo(o.getName());
    }

    public String getOperandeLeft() {
        return operandeLeft;
    }

    public String getOperandeRight() {
        return operandeRight;
    }

    public String getOperation() {
        return operation;
    }
    
    
}
