package Tiles;

import Basis.GameObject;
import Basis.Tile;

import java.awt.image.BufferedImage;

public class Coin extends Tile {
    protected Coin(BufferedImage image, int tileSize, int x, int y) {
        super(image, tileSize, x, y);
    }

    @Override
    protected void verticalCollision(GameObject object) {

    }

    @Override
    protected void horizontalCollision(GameObject object) {

    }
}
