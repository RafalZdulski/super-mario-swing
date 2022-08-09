package Basis;

import java.awt.*;

public class RigidBody {
    GameObject obj;
    /**Collision box dimensions and positioning*/
    protected int cWidth;
    public int cHeight;
    protected int xOffset;
    protected int yOffset;
    /**Collision boxes*/
    Rectangle cBox;
    Rectangle cBoxX;
    Rectangle cBoxY;

    public void setCollisionBox(int cWidth,int cHeight, int xOffset, int yOffset){
        this.cWidth = cWidth;
        this.cHeight = cHeight;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public RigidBody(GameObject gameObject){ this.obj = gameObject; }

    /** for moving NPC and player**/
    public void dynamicUpdate(){
        calculateDynamicBox();
        checkCollisionWithTiles();
        checkCollisionWithNPC();
    }
    public void calculateDynamicBox(){
        cBox = new Rectangle(obj.x-cWidth/2,obj.y-cHeight/2, cWidth, cHeight);
        cBoxX = new Rectangle((int)(obj.x-cWidth/2+obj.dx),obj.y-cHeight/2, cWidth, cHeight);
        cBoxY = new Rectangle(obj.x-cWidth/2,(int)(obj.y-cHeight/2+obj.dy), cWidth, cHeight);
    }
    private void checkCollisionWithTiles(){
        for (Tile tile : obj.level.tileList){
            if(tile.rigidBody.cBox.intersects(this.cBoxY)){
                obj.verticalCollision(tile);
                tile.verticalCollision(obj);
            }else if(obj.mayFall){
                obj.falling = true;
            }
            if(tile.rigidBody.cBox.intersects(this.cBoxX)){
                obj.horizontalCollision(tile);
                tile.horizontalCollision(obj);
            }

        }
    }
    private void checkCollisionWithNPC(){
        for (NPC npc : obj.level.NPCList ){
            if(npc.rigidBody.cBox.intersects(this.cBoxY)){
                obj.verticalCollision(npc);
                npc.verticalCollision(obj);
            }
            if(npc.rigidBody.cBox.intersects(this.cBoxX)){
                obj.horizontalCollision(npc);
                npc.horizontalCollision(obj);
            }
        }
    }

    /** for immovable NPC and Tiles **/
    public void calculateStaticBox(){
        cBox = new Rectangle(obj.x-cWidth/2+xOffset,obj.y-cHeight/2+yOffset, cWidth, cHeight);
    }



}
