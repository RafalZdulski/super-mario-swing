package Tiles;

import Basis.GameObject;
import Basis.Tile;
import NPCs.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OrdinaryBrick extends Tile {
    private int dy;

    public OrdinaryBrick(BufferedImage image, int size, int x, int y) {
        super(image, size,x,y);
    }

    @Override
    protected void verticalCollision(GameObject object) {
        if(object instanceof Player && object.y > y){
            if(((Player) object).powerUP == 0){
                dy = -height/3;
            }
        }
    }

    @Override
    public void draw(Graphics2D g, int x, int y){
        g.drawImage(image, x, y+dy, width, height, null);
        dy += (dy != 0) ? 1 : 0;

    }

    @Override
    protected void horizontalCollision(GameObject object) {

    }
}
