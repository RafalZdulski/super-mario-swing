package Tiles;

import Basis.GameObject;
import Basis.Tile;
import java.awt.image.BufferedImage;

public class GroundBrick extends Tile {

    public GroundBrick(BufferedImage image, int size,int x, int y) {
        super(image, size,x,y);
    }


    @Override
    protected void verticalCollision(GameObject object) {

    }

    @Override
    protected void horizontalCollision(GameObject object) {

    }
}
