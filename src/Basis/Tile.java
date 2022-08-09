package Basis;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile extends GameObject{

    protected Tile(BufferedImage image, int tileSize, int x, int y) {
        super();
        this.image = image;
        this.width = tileSize;
        this.height = tileSize;
        this.x = x+width/2;
        this.y = y+height/2;
        rigidBody.setCollisionBox(width,height,0,0);
        rigidBody.calculateStaticBox();
    }


    public void draw(Graphics2D g, int x, int y){
        g.drawImage(image,x,y,width,height,null);
    }

}
