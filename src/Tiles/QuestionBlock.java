package Tiles;

import Basis.GameObject;
import Basis.Level;
import Basis.Tile;
import Main.Game;
import Main.GamePanel;
import NPCs.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class QuestionBlock extends Tile {
    public static final int NOTHING = 0;
    public static final int COINS = 1;
    public static final int ONEUP = 2;
    public static final int POWERUP = 3;
    public static final int FLOWER = 4;
    public static final int STAR = 5;

    protected int amount;
    protected int type;

    protected BufferedImage used;
    protected BufferedImage coin;
    protected int time;
    protected boolean spawnCoin;

    protected int dy;
    public QuestionBlock(Level level, BufferedImage image, int size, int x, int y, int type) {
        super(image, size,x,y);
        this.level = level;
        this.type = type;
        switch (this.type){
            case NOTHING:amount=0;break;
            case COINS:amount=5;time = 10;break;
            default:amount=1;
        }
        getSprites();
    }

    @Override
    protected void verticalCollision(GameObject object) {
        if(object instanceof Player && object.y > y){
            dy = -height/3;
            spawn();
        }
    }

    private void spawn() {
        if (amount-- > 0)
            switch (type){
                case COINS: spawnCoin = true; break;
                case ONEUP: break;
                case POWERUP: break;
            }
        else
            image = used;
    }

    protected void getSprites(){
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/NPCs/Sprite.png"));
            used = spriteSheet.getSubimage(0,48,48,48);
            coin = spriteSheet.getSubimage(0,144+48,48,48);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y){
        g.drawImage(image, x, y+dy, width, height, null);
        dy += (dy != 0) ? 1 : 0;

        if(spawnCoin && time-- > 0)
            g.drawImage(coin, x + width / 3, y - height, width * 2 / 3, height * 2 / 3, null);
        else if(spawnCoin && time < 0) {
            spawnCoin = false;
            time = 10;
        }

    }

    @Override
    protected void horizontalCollision(GameObject object) {

    }
}
