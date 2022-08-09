package NPCs;

import Basis.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Goomba extends NPC {
    public Goomba(Level level, int x, int y) {
        super(level);
        this.x = x;
        this.y = y;
        width = level.tileSize;
        height = level.tileSize;
        moveSpeed = 0.5;
        maxSpeed = 1;
        maxFallSpeed = 14.0;
        fallSpeed = 0.8;
        rigidBody.setCollisionBox(width-2,height-2,0,0);
        rigidBody.calculateDynamicBox();
        mayFall = true;
        animation = new Animation();
        getSprites();
        delay = new Delay();
    }

    @Override
    public void update() {
        move();
        rigidBody.dynamicUpdate();
        animate();
    }

    private void move(){
        if(die) return;

        if(y > level.mapHeight)
            ;/** DEATH BY FALLING OF THE EDGE IS REQUIRED **/
        x+=dx;
        y+=dy;
        if(facingRight){
            dx += moveSpeed;
            if(dx > maxSpeed) dx = maxSpeed;
        }else{
            dx -= moveSpeed;
            if(dx < -maxSpeed) dx = -maxSpeed;
        }
        if(falling){
            dy += fallSpeed;
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    @Override
    public void animate() {
        animation.update();
    }
    @Override
    public void draw(Graphics2D g) {
        image = animation.getImage();
        if(!die) {
            g.drawImage(image, x - level.x - width / 2, y - level.y - height / 2, width, height, null);
        }else{
            g.drawImage(image, x - level.x - width / 2, y - level.y + height / 4, width, height/4, null);
            if(delay.passed())
                level.removeNPC(this);
        }
    }
    @Override
    public void getSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/NPCs/sprite.png"));
            BufferedImage[] frames = new BufferedImage[2];
            frames[0] = spriteSheet.getSubimage(48,144,48,48);
            frames[1] = spriteSheet.getSubimage(192,144,48,48);
            animation.setFrames(frames);
            animation.setDelay(250);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void verticalCollision(GameObject object) {
        if(object instanceof Tile){
            if(dy > 0 ) {
                setY(object.y-object.height/2-this.rigidBody.cHeight/2);
                dy = fallSpeed;
            }else {
                setY(object.y+object.height/2+this.rigidBody.cHeight/2);
                dy = 0;
            }
        }else if(object instanceof Player){
            if(object.y < y){
                //player was higher than goomba so goomba needs to die
                die = true;
                delay.setDelay(400);
                update();
                rigidBody.setCollisionBox(0,0,0,0);
            }
        }
    }
    @Override
    protected void horizontalCollision(GameObject object) {
        if(object instanceof Tile || object instanceof Goomba){
            dx = -dx;
            facingRight = !facingRight;
        }
    }
}
