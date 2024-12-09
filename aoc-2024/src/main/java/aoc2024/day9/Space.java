package aoc2024.day9;

public class Space {
    private boolean isEmpty;
    private Long id;
    
    public boolean isEmpty() {
        return isEmpty;
    }
    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public static Space empty() {
        Space s = new Space();
        s.isEmpty = true;
        return s;
    }
    public static Space file(Long id) {
        Space s = new Space();
        s.id = id;
        s.isEmpty = false;
        return s;
    }
    @Override
    public String toString() {
        if(isEmpty) {
            return "EMPTY";
        } else {
            return ""+ id;
        }
       
    }
    
    
}
