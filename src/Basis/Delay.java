package Basis;

public class Delay {
    private long startTime;
    private long delay;

    public void setDelay(long d) {
        delay = d;
        startTime = System.nanoTime();
    }

    public boolean passed() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay)
            return true;
        else
            return false;
    }

}
