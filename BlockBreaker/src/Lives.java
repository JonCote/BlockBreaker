import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Lives {
    
    private int x;
    private int y;
    private int width;
    private int height;

    private BufferedImage lives;
    SpriteSheet ss;


    public Lives(int x, int y, int width, int height, Game game){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.ss = new SpriteSheet(game.getSpriteSheet(2));
        lives = ss.grabImage(0, 0, width, height);
    }

    public void render(Graphics g){
        g.drawImage(lives, x, y, null);

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

    public void setImage(int x, int y){
        lives = ss.grabImage(x, y, width, height);
    }

}



