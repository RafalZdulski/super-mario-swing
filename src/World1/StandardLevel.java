package World1;

import Basis.GameState;
import Basis.Level;
import Basis.NPC;
import Basis.Tile;
import NPCs.Flagpole;
import NPCs.Goomba;
import Main.GamePanel;
import NPCs.Player;
import Tiles.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StandardLevel extends Level {

    StandardLevel(int tileSize, GameState worldState, String mapPath){
        super(tileSize,worldState);
        loadIDMap(mapPath);
        init();
    }
    protected void init(){
        loadBackground("/World/World1bg.png");
        loadTilesImages();
        tileMap = new Tile[numCols][numRows];
        NPCList = new ArrayList<>();
        loadTileMap();
    }

    /** NPC ID Definitions **/
    int playerID = new Color(160, 160, 160).getRGB();
    int goombaID = new Color(0, 255, 255).getRGB();
    int koopaID = new Color(255, 0, 255).getRGB();
    int pipeID = new Color(0, 255, 0).getRGB();
    int flagID = new Color(160, 0, 160).getRGB();


    /** immovable Tiles ID definitions **/
    int ordinaryBrickID = new Color(0, 0, 255).getRGB();
    int questionBlockID = new Color(255, 255, 0).getRGB();
    int groundBrickID = new Color(255, 0, 0).getRGB();
    int floorBrickID = new Color(127, 51, 0).getRGB();
    /** static Tiles images **/
    private BufferedImage ordinaryBrickImage;
    private BufferedImage questionBlockImage;
    private BufferedImage groundBrickImage;
    private BufferedImage floorBrickImage;

    @Override
    protected void loadTilesImages() {
        try{
            BufferedImage spriteSheet  = ImageIO.read(getClass().getResourceAsStream("/World/TilesSpriteSheet.png"));
            int s = 16; //SpriteTileSize shortened to just "s"
            ordinaryBrickImage = spriteSheet.getSubimage(2*s,0*s,s,s);
            questionBlockImage = spriteSheet.getSubimage(24*s,0*s,s,s);
            floorBrickImage = spriteSheet.getSubimage(0*s,0*s,s,s);
            groundBrickImage = spriteSheet.getSubimage(0*s,1*s,s,s);
        }catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    protected void loadTileMap() {
        for (int x = 0; x < numCols; x++) {
            for (int y = 0; y < numRows; y++) {
                if(colorMap[x][y] == ordinaryBrickID)
                    tileMap[x][y] = new OrdinaryBrick(ordinaryBrickImage,tileSize,x*tileSize,y*tileSize);
                else if(colorMap[x][y] == questionBlockID)
                    tileMap[x][y] = new QuestionBlock(this,questionBlockImage,tileSize,x*tileSize,y*tileSize,QuestionBlock.COINS);
                else if(colorMap[x][y] == groundBrickID)
                    tileMap[x][y] = new GroundBrick(groundBrickImage,tileSize,x*tileSize,y*tileSize);
                else if(colorMap[x][y] == floorBrickID)
                    tileMap[x][y] = new GroundBrick(floorBrickImage,tileSize,x*tileSize,y*tileSize);
                else if(colorMap[x][y] == flagID)
                    NPCList.add(new Flagpole(this,x*tileSize+tileSize/2,y*tileSize+tileSize/2));
                else  if(colorMap[x][y] == goombaID)
                    NPCList.add(new Goomba(this,x*tileSize+tileSize/2,y*tileSize+tileSize/2-3));
                else if(colorMap[x][y] == playerID)
                    setPlayerStertingPoint(x*tileSize+tileSize/2,y*tileSize+tileSize/2);
                else
                    ;

            }
        }
        updateTileList();
    }

    @Override
    public void draw(Graphics2D g) {
        //background
        g.drawImage(bg1,0,0,GamePanel.WIDTH,GamePanel.HEIGHT,null);
        //drawing only that part of map which is rendered
        for(int row = rowOffset,i=0; row < rowOffset + numRowsToDraw; row++,i++) {
            if(row >= numRows || i > numRowsToDraw) break;
            for(int col = colOffset,j=0; col < colOffset + numColsToDraw; col++,j++) {
                if(col >= numCols|| j > numColsToDraw) break;
                if(tileMap[col][row] != null)
                    tileMap[col][row].draw(g,-(x%tileSize) + j * tileSize, -(y%tileSize) + i * tileSize);
            }
        }
        for (NPC npc : NPCList) {
            npc.draw(g);
        }
    }

    /**Imitating movement of background*/
    int xbg;
    int ybg;
    BufferedImage bg1;

    @Override
    public void setBgXY(int x, int y){
        if(x < 0) x = 0;
        if(x + GamePanel.WIDTH > mapWidth) x = mapWidth - GamePanel.WIDTH;
        if(y < 0) y = 0;
        if(y + GamePanel.HEIGHT > mapHeight) y = mapHeight - GamePanel.HEIGHT;
        double ytemp = ((double)y/(double)mapHeight)/2;
        double xtemp = ((double)x/(double)mapWidth)/2;
        ybg =(int)( background.getHeight() * ytemp) ;
        xbg =(int)( background.getWidth() * xtemp);
        if(ybg > background.getHeight() - GamePanel.HEIGHT) ybg =  background.getHeight() - GamePanel.HEIGHT;
        if(xbg > background.getWidth() - GamePanel.WIDTH) xbg =  background.getWidth() - GamePanel.WIDTH;
        bg1 = background.getSubimage(xbg,ybg,GamePanel.WIDTH,GamePanel.HEIGHT);
    }

}
