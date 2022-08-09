package Basis;

import NPCs.Player;
import GameStates.GameStateManager;

import java.awt.image.BufferedImage;

public abstract class GameState {
    protected GameStateManager gsm;
    protected BufferedImage background;

    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);

    /** for world states **/
    public abstract void goToNextLevel();
    public abstract void restartLevel();
    public boolean gameOver;
}
