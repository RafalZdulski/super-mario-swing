package NPCs;

import Basis.*;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends NPC {
    private boolean canJump;
    public int powerUP = 0;
    private ArrayList<BufferedImage[]> smallMarioSprites;
    private ArrayList<BufferedImage[]> bigMarioSprites;
    private ArrayList<BufferedImage[]> fireMarioSprites;

    public Player(Level level) {
        super(level);
        powerUP = 0;
        /** attributes **/
        width = level.tileSize;
        height = level.tileSize;
        moveSpeed = 0.4;
        maxSpeed = 8.0;
        stopSpeed = 0.3;
        fallSpeed = 0.8;
        maxFallSpeed = 14.0;
        jumpStart = -15.0;
        stopJumpSpeed = 0;
        mayFall = true;
        /** rigidbosy **/
        rigidBody.setCollisionBox(width-4,height-4,0,0);
        rigidBody.calculateDynamicBox();
        /** Animations **/
        getSprites();
        delay = new Delay();
        /** Spawn Positions**/
        x = level.playerSpawnX;
        y = level.playerSpawnY;
    }
    @Override
    public void update() {
        move();
        rigidBody.dynamicUpdate();
        level.setRenderPosition((x-GamePanel.WIDTH/2), (y-GamePanel.HEIGHT/2));
        level.setBgXY(x-GamePanel.WIDTH/2,y-GamePanel.HEIGHT/2);
        animate();
        if(y > level.mapHeight+height){
            level.worldState.restartLevel();
        }
    }

    /** Collisions **/
    @Override
    protected void horizontalCollision(GameObject object) {
        if(object instanceof Tile){
            dx = 0;
        }else if(object instanceof Flagpole){
            dx = 0;
            currentAction = ONFLAG;
            x = object.x-width/2;
            dy = 2;
            y += dy;
            if(object.y <= y){
                y = object.y;
                level.worldState.goToNextLevel();
            }
        }else if(object instanceof Goomba){
            gotHit();
        }
    }
    @Override
    protected void verticalCollision(GameObject object) {
        if(object instanceof Tile){
            if(dy > 0 ) {
                setY(object.y-object.height/2-this.rigidBody.cHeight/2);
                canJump = true;
                dy = fallSpeed;
            }else {
                setY(object.y+object.height/2+this.rigidBody.cHeight/2);
                dy = 0;
            }
        }else if(object instanceof Goomba){
            if(object.y > y){
                //player was higher than goomba so goomba needs to die
                dy = jumpStart/4;
                //it would be nice of player would able to jump off goomba
            }else {
                gotHit();
            }
        }
    }

    private void gotHit() {
        if(powerUP == 0){
            //small mario
            currentAction = DYINGORCROUCHING;
            dx = 0; dy =0;
            die = true;
            //update();
            rigidBody.setCollisionBox(0,0,0,-30);
            delay.setDelay(200);
        }else if(powerUP == 1){
            //big mario
            ;
        }
    }

    /** Movement **/
    private void move(){
        if(currentAction == DYINGORCROUCHING && powerUP == 0)
            return;
        if(currentAction == ONFLAG)
            return;
        if( x+dx-width/2 > 0 && x+dx+width/2 < level.mapWidth)
            x += (int)dx;
        y += (int)dy;
        if(y > level.mapHeight)
            ;/** DEATH BY FALLING OF THE EDGE IS REQUIRED **/
        if(right){
            if(dx < 0)
                dx += stopSpeed;
            facingRight = true;
            dx += moveSpeed;
            if(dx > maxSpeed) dx = maxSpeed;
        }
        else if(left){
            if(dx > 0)
                dx -= stopSpeed;
            facingRight = false;
            dx -= moveSpeed;
            if(dx < -maxSpeed) dx = -maxSpeed;
        }
        else{
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) dx = 0;
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) dx = 0;
            }
        }
        if(canJump && jumping){
            dy = jumpStart - Math.abs(dx/4);
            canJump = false;
            falling = true;
        }

        if(falling){
            if(fallSpeed > moveSpeed) canJump = false;
            dy += fallSpeed;
            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setJumping(boolean b) { jumping = b; }

    /** Animation **/
    @Override
    public void draw(Graphics2D g) {
        BufferedImage frame = animation.getImage();
        if(delay.passed() && die){
            g.drawImage(frame,x-level.x-width/2,y-level.y-height/2,width,height, null);
            y += 3;
        }else if(facingRight)
            g.drawImage(frame,x-level.x-width/2,y-level.y-height/2,width,height, null);
        else
            g.drawImage(frame,x-level.x-width/2+width,y-level.y-height/2,-width,height, null);
    }
    @Override
    public void getSprites() {
        int xOffset = 80;
        int smallMarioHeight = 16;
        int bigMarioHeight = 32;
        int marioWidth = 16;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/NPCs/mario-luigi-2.png"));
            smallMarioSprites = new ArrayList<>();
            bigMarioSprites = new ArrayList<>();

            int l;
            l = 0;
            for (int j=0; j<numFrames.length; j++) {
                BufferedImage[] B = new BufferedImage[numFrames[j]];
                for (int k = 0; k < numFrames[j]; k++, l++) {
                    B[k] = spriteSheet.getSubimage(xOffset + marioWidth * l, 0, marioWidth, bigMarioHeight);
                }
                bigMarioSprites.add(B);
            }
            l=0;
            for (int j=0; j<numFrames.length; j++) {
                BufferedImage[] B = new BufferedImage[numFrames[j]];
                for (int k = 0; k < numFrames[j]; k++, l++) {
                    B[k] = spriteSheet.getSubimage(xOffset + marioWidth * l, bigMarioHeight, marioWidth, smallMarioHeight);
                }
                smallMarioSprites.add(B);
            }
        } catch (IOException e) { e.printStackTrace(); }
        sprites = smallMarioSprites;
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(-1);
    }
    boolean touchFlagPole = false;
    @Override
    public void animate() {
        if(currentAction == DYINGORCROUCHING ) {
            animation.setFrames(sprites.get(DYINGORCROUCHING));
            animation.setDelay(-1);
        }else if(currentAction == ONFLAG){
            //onflag
            if(!touchFlagPole) {
                facingRight = true;
                touchFlagPole = true;
                animation.setFrames(sprites.get(ONFLAG));
                animation.setDelay(180);
            }
        }else if(!canJump){
            //jumping
            currentAction = JUMPING;
            animation.setFrames(sprites.get(JUMPING));
            animation.setDelay(-1);
        }else if((left && dx > 2*moveSpeed) || (right && dx < -2*moveSpeed)){
            //stopping
            currentAction = STOPPING;
            animation.setFrames(sprites.get(STOPPING));
            animation.setDelay(-1);
        }else if((left || right) && canJump) {
            //walking
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(350);
            }else{
                int maxDelay = 350; // for min speed
                int minDelay = 50; // for max speed
                double a = (maxDelay-minDelay)/(moveSpeed-maxSpeed);
                double b = minDelay-a*maxSpeed;
                animation.setDelay((long) (a*Math.abs(dx)+b));
            }
        }else if(currentAction != IDLE && dx == 0 && canJump){
            //idle
            currentAction = IDLE;
            animation.setFrames(sprites.get(IDLE));
            animation.setDelay(-1);
        }
        animation.update();
    }
    /**Animations data**/
    private int currentAction;
    private static final int WALKING = 0;
    private static final int STOPPING = 1;
    private static final int JUMPING = 2;
    private static final int DYINGORCROUCHING = 3;
    private static final int IDLE = 4;
    private static final int ONFLAG = 5;
    private static final int GETHIT = 6;
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {3, 1, 1, 1, 1, 2};

}
