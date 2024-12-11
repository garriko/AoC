package aoc2024.day11;

public class Stone implements Comparable<Stone> {
    private Long number;
    private Long occurence;

    public Stone(long number) {
        this.number = number;
        this.occurence = 1L;
    }

    public Stone(Long number, Long occurence) {
        this.number = number;
        this.occurence = occurence;
    }

    public void addOccurence(Long occurence) {
        this.occurence += occurence;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getOccurence() {
        return occurence;
    }

    public void setOccurence(Long occurence) {
        this.occurence = occurence;
    }

    public void multiplyNumber() {
        number *= 2024;

    }

    @Override
    public int compareTo(Stone o) {
        return Long.compare(this.number, o.number);
    }

}
