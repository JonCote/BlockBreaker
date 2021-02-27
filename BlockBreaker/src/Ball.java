import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ball {
    
    private int x;
    private int y;
    private int width;
    private int height;

    private double velX = 5;
    private double velY = 5;

    private BufferedImage ball;

    public Ball(int x, int y, int width, int height, Game game){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet(1));

        ball = ss.grabImage(351, 163, width, height);
    }

    public void tick(){
        x+=velX;
        y+=velY;

        if(x <= 0){
            velX = 5;
        }
        if(x >= 950){
            velX = -5;
        }
        if(y <= 0){
            velY = 5;
        }
        if(y >= 1500){
            velY = -5;
        }
    }

    public void render(Graphics g){
        g.drawImage(ball, x, y, null);

    }

    // only kinda works needs fixing (physics is bad currently)
    public void collision(){
        velY = velY*-1;
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }

    public void resetPos(){
        this.x = 470;
        this.y = 655;
        this.velY = 5;
    }

}

