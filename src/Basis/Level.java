package Basis;

import Main.GamePanel;
import Tiles.Coin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    public GameState worldState;

    protected Level(int tileSize, GameState worldState){
        this.worldState = worldState;
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
    }
    /** NPC list **/
    public ArrayList<NPC> NPCList;

    /** Background **/
    protected BufferedImage background;
    protected void loadBackground(String path){
        try{
            background = ImageIO.read(getClass().getResourceAsStream(path));
        }catch(Exception e) { e.printStackTrace(); }
    }
    public abstract void setBgXY(int x, int y);

    /**Map**/
    protected int numCols;
    protected int numRows;
    public int tileSize;
    protected int[][] colorMap;
    public int mapWidth;
    public int mapHeight;
    protected void loadIDMap(String path){
        BufferedImage mapImage = null;
        try{
            mapImage = ImageIO.read(getClass().getResourceAsStream(path));
            numCols = mapImage.getWidth();
            numRows = mapImage.getHeight();
            mapWidth = numCols * tileSize;
            mapHeight = numRows * tileSize;
        }catch(Exception e) { e.printStackTrace(); }
        colorMap = new int[numCols][numRows];

        for (int x = 0; x < numCols; x++) {
            for (int y = 0; y < numRows; y++) {
                colorMap[x][y] = mapImage.getRGB(x, y);
            }
        }
        xmax = numCols*tileSize - GamePanel.WIDTH;
        ymax = numRows*tileSize - GamePanel.HEIGHT;
    }

    /** Tile map**/
    protected Tile[][] tileMap;
    public ArrayList<Tile> tileList;
    protected void updateTileList(){
        tileList = new ArrayList<>();
        for (Tile[] partTileMap : tileMap)
            for (Tile tile : partTileMap)
                if (tile != null)
                    tileList.add(tile);
    }
    protected abstract void loadTilesImages();
    protected abstract void loadTileMap();

    /**Player Spawn**/
    public int playerSpawnX;
    public int playerSpawnY;
    public void setPlayerStertingPoint(int x, int y){
        playerSpawnX = x;
        playerSpawnY = y;
    }

    /**Rendering**/
    protected int xmax;
    protected int ymax;
    public int x;
    public int y;
    protected int rowOffset;
    protected int colOffset;
    protected int numRowsToDraw;
    protected int numColsToDraw;
    public void setRenderPosition(int x, int y) {
        this.x = x;
        this.y = y;
        fixBounds();
        colOffset = this.x / tileSize;
        rowOffset = this.y / tileSize;
    }
    protected void fixBounds() {
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }


    public abstract void draw(Graphics2D g);

    public void removeNPC(NPC npc) {
        ArrayList<NPC> temp = (ArrayList<NPC>) NPCList.clone();
        temp.remove(npc);
        NPCList = temp;

    }
}
