package GameStates;

import Basis.GameState;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {
    /**picture of mushroom that signals chosen option in menu**/
    private BufferedImage mushroom;

    /**menu options**/
    private int currentChoice = 0;
    private String[] options = {
            "START",
            " HELP",
            " EXIT"
    };

    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
        //download background image
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/Menu/menubg.png"));
            mushroom = ImageIO.read(getClass().getResourceAsStream("/Menu/menuMushroom.png"));
        } catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    public void draw(Graphics2D g) {
        int middleW = GamePanel.WIDTH/2;
        int middleH = GamePanel.HEIGHT*2/3;
        //draw background
        g.drawImage(background,0,0, GamePanel.WIDTH, GamePanel.HEIGHT,null);
        //draw menu options
        g.setFont(new Font("arial", Font.BOLD, 14));
        g.setColor(new Color(255, 255, 255));
        for(int i = 0; i < options.length; i++) {
            if(i == currentChoice)
                g.drawImage(mushroom, middleW-50, middleH-45 + i * 20,20,20 ,null);
            g.drawString(options[i], middleW-25, middleH-30 + i * 20);
        }
    }

    private void select() {
        switch(currentChoice) {
            case 0: gsm.setState(GameStateManager.PLAYGAME); break;
            case 1: gsm.setState(GameStateManager.HELPSCREEN); break;
            case 2: System.exit(0); break;
        }
    }

    @Override
    public void keyPressed(int k) {
        switch (k){
            case KeyEvent.VK_ENTER: select(); break;
            case KeyEvent.VK_UP: if(--currentChoice == -1) currentChoice = options.length-1; break;
            case KeyEvent.VK_DOWN: if(++currentChoice == options.length) currentChoice = 0; break;
        }
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
