package Basis;

import java.awt.*;

public abstract class NPC extends GameObject{
    public NPC(Level level){
        super(level);
        this.level = level;
    }
    public abstract void update();

    /**movement*/
    public boolean left;
    public boolean right;
    public boolean jumping;

    /**movement attributes*/
    public double moveSpeed;
    public double maxSpeed;
    public double stopSpeed;
    public double fallSpeed;
    public double maxFallSpeed;
    public double jumpStart;
    public double stopJumpSpeed;

    /** Animation **/
    protected boolean facingRight;
    protected Delay delay;
    public abstract void animate();
    public abstract void draw(Graphics2D g);
    public abstract void getSprites();

    protected boolean die = false;
}
