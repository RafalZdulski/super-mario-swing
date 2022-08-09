package Basis;

import java.awt.image.BufferedImage;

public abstract class GameObject {
    /**level info*/
    public Level level;

    /**position and shift vector*/
    public int x; //object centre
    public int y; //object centre
    public double dx;
    public double dy;

    /**sprite*/
    public int width;
    public int height;
    protected BufferedImage image;

    /** Constructor for NPC and Player **/
    public GameObject(Level level){
        this.level = level;
        rigidBody = new RigidBody(this);
    }
    /** booleans for objects affected by gravitation **/
    public boolean falling;
    public boolean mayFall;

    /**Constructor for immovable Tiles**/
    public GameObject(){ rigidBody = new RigidBody(this); }

    /** rigidbody is responsible for collisions and physic **/
    protected RigidBody rigidBody;
    protected abstract void verticalCollision(GameObject object);
    protected abstract void horizontalCollision(GameObject object);

    protected Animation animation;

    public void setXY(int x, int y){ this.x = x; this.y = y; }
    public void setX(int x){ this.x = x; }
    public void setY(int y){ this.y = y; }

}
