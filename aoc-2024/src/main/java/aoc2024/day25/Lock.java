package aoc2024.day25;

import java.util.List;

public class Lock extends KeyLock {
    public static Lock fromInput(List<String> input) {
        Lock lock = new Lock();        
        lock.parse(input);        
        return lock;
    }

    public long testKeyList(List<Key> keyList) {
        return keyList.stream().filter(this::keyFit).count();
    }
    
    private boolean keyFit(Key key) {
        boolean fit = true;
        for(int i = 0 ; i< 5; i++) {
            if(heightArray[i] + key.heightArray[i] > 5) {
                fit = false;
                break;
            }
        }
        return fit;
    }
}
