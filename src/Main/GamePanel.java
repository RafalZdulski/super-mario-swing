package Main;

import GameStates.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    /**game thread**/
    private Thread thread;
    private boolean running;
    private int FPS=30;
    private int targetTime = 1000/FPS;

    /**game resolution**/
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final int SCALE = 2;

    /**game state manager**/
    private GameStateManager gsm;

    /**image**/
    private BufferedImage image;
    private Graphics2D g;

    /**Constructor**/
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setFocusable(true);
        requestFocus();
    }

    /**new thread + key listener**/
    public void addNotify(){
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            addKeyListener (this);
            thread.start();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) { gsm.keyPressed(e.getKeyCode());}
    @Override
    public void keyReleased(KeyEvent e) { gsm.keyReleased(e.getKeyCode()); }

    @Override
    public void run() {
        //init()
        running = true;
        gsm = new GameStateManager();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        long startTime;
        long elpTime;
        long waitTime;
        //Main game loop
        while(running){
            startTime = System.nanoTime();
            //update
            gsm.update();
            gsm.draw(g);
            Render();

            //wait
            elpTime = System.nanoTime()-startTime;
            waitTime = targetTime - elpTime/1000000 ;
            if(waitTime < 0)
                waitTime = 5;
            try{ Thread.sleep(waitTime); } catch(Exception e){ e.printStackTrace(); }
        }
    }

    private void Render () {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }
}
