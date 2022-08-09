package Basis;

import java.awt.image.BufferedImage;

public class Animation {
    /**zmienna przechowujaca tablice spritow 1 czynnnosc */
    private BufferedImage[] frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    public void setDelay(long d) { delay = d; }
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    /**  funkcja zmieniajaca klatki animacji*/
    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(delay == -1)return;
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
        }
    }
    public BufferedImage getImage() { return frames[currentFrame]; }
}
