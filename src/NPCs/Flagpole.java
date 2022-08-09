package NPCs;

import Basis.GameObject;
import Basis.Level;
import Basis.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Flagpole extends NPC {
    private int flagpoleHeight = 7; //length expressed in tiles
    private int tileHeight;
    private int flagDown = 0;

    public Flagpole(Level level, int x, int y){
        super(level);
        this.x = x;
        this.y = y;
        this.width = level.tileSize;
        tileHeight = level.tileSize;
        this.height = level.tileSize*flagpoleHeight;
        rigidBody.setCollisionBox(width/2, (flagpoleHeight-1)*tileHeight,0,-(flagpoleHeight-2)*tileHeight/2);
        rigidBody.calculateStaticBox();
        getSprites();
    }
    @Override
    public void update() {}

    /** Collisions **/
    @Override
    protected void verticalCollision(GameObject object) {}
    @Override
    protected void horizontalCollision(GameObject object) {
        if(object instanceof Player){
            if(object.y < y)
                flagDown += 2;
        }
    }

    /** Animations **/
    BufferedImage top;
    BufferedImage pole;
    BufferedImage flag;
    @Override
    public void animate(){ }
    @Override
    public void draw(Graphics2D g) {
        int tempX = x-level.x-width/2;
        int tempY = y-level.y-height+tileHeight/2;

        g.drawImage(top,tempX,tempY, width,tileHeight,null);
        for(int i = 1; i<=flagpoleHeight-1; i++)
            g.drawImage(pole,tempX,tempY+i*tileHeight, width, tileHeight,null);
        g.drawImage(flag,tempX-width/2,tempY+tileHeight+flagDown, width, tileHeight,null);
    }
    @Override
    public void getSprites() {
        int row = 9;
        int col = 16;
        int s = 16;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/World/TilesSpriteSheet.png"));
            pole = spriteSheet.getSubimage(col*s,row*s,s,s);
            top = spriteSheet.getSubimage(col*s,(row+1)*s,s,s);
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/NPCs/sprite.png"));
            flag = spriteSheet.getSubimage(192,0,48,48);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
