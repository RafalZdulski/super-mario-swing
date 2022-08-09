package GameStates;

import Basis.GameState;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class HelpScreenState extends GameState {

    public HelpScreenState(GameStateManager gsm){
        this.gsm = gsm;
        //download background image
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/Menu/helpbg.png"));
        }catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    public void draw(Graphics2D g) {
        //draw background
        g.drawImage(background,0,0, GamePanel.WIDTH, GamePanel.HEIGHT,null);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) gsm.setState(GameStateManager.MENUSTATE);
        if (k == KeyEvent.VK_ESCAPE) gsm.setState(GameStateManager.MENUSTATE);
    }

    @Override
    public void init() {}
    @Override
    public void update() {}
    @Override
    public void keyReleased(int k) {}
    @Override
    public void goToNextLevel() {}
    @Override
    public void restartLevel() {}


}
