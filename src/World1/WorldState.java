package World1;

import Basis.GameState;
import Basis.NPC;
import NPCs.Player;
import GameStates.GameStateManager;
import Basis.Level;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class WorldState extends GameState {
    protected ArrayList<Level> levels = new ArrayList<>();
    public Player player;
    protected int currentLevel = 0;
    protected BufferedImage worldScreen;
    protected BufferedImage gameOverScreen;
    int world = 1;
    int lives = 3;
    private boolean wrldScreen = true;


    public WorldState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
        try {
            worldScreen = ImageIO.read(getClass().getResourceAsStream("/World/worldScreen.png"));
            gameOverScreen = ImageIO.read(getClass().getResourceAsStream("/World/gameOver.jpg"));
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void init(){
        levels.add(new StandardLevel(20, this, "/World/Level1map.png"));
        levels.add(new StandardLevel(20, this, "/World/Level2map.png"));
        player = new Player(levels.get(currentLevel));
    }

    @Override
    public void update() {
        player.update();
        for (NPC npc : levels.get(currentLevel).NPCList)
            npc.update();
    }

    @Override
    public void draw(Graphics2D g){
        if(wrldScreen){
            displayWorldScreen(g);
        }else if(gameOver){
            g.drawImage(gameOverScreen,0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
        }else {
            levels.get(currentLevel).draw(g);
            player.draw(g);
        }
    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ESCAPE) gsm.setState(GameStateManager.MENUSTATE);
        if(k == KeyEvent.VK_ENTER && wrldScreen) wrldScreen = false;
        if(k == KeyEvent.VK_ENTER && gameOver) gsm.setState(GameStateManager.MENUSTATE);
        if(k == KeyEvent.VK_UP) player.setJumping(true);
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);

    }

    @Override
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_UP) player.setJumping(false);
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
    }

    @Override
    public void goToNextLevel() {
        currentLevel++;
        if (currentLevel > levels.size()) {
            gameOver = true;
        }else{
            player = new Player(levels.get(currentLevel));
            wrldScreen = true;
        }

    }

    @Override
    public void restartLevel() {
        lives--;
        if(lives >0){
            wrldScreen = true;
            levels = new ArrayList<>();
            init();
        }else{
            gameOver=true;
        }
    }

    public void displayWorldScreen(Graphics2D g){
        g.drawImage(worldScreen,0,0, GamePanel.WIDTH,GamePanel.HEIGHT,null);
        Color color = new Color(255, 255, 255);
        Font font = new Font("arial", Font.BOLD, 32);
        g.setColor(color);
        g.setFont(font);
        g.drawString(""+world,340,150);
        g.drawString(""+(currentLevel+1),380,150);
        g.drawString(""+lives,360,240);
    }

}
