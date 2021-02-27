import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
    
    private int x;
    private int y;
    private int width;
    private int height;

    private double velX = 0;

    private BufferedImage player;

    public Player(int x, int y, int width, int height, Game game){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet(1));

        player = ss.grabImage(0, 227, width, height);
    }

    public void tick(){
        x+=velX;

        if(x <= 0){
            x = 0;
        }
        if(x >= 873){
            x = 873;
        }
    }

    public void render(Graphics g){
        g.drawImage(player, x, y, null);

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

    public void resetPos(){
        this.x = 434;
        this.y = 695;
    }


}
