import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Block {
    
    private int x;
    private int y;
    private int width;
    private int height;
    private int hp = 2;

    private BufferedImage block;
    SpriteSheet ss;


    public Block(int x, int y, int width, int height, Game game){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.ss = new SpriteSheet(game.getSpriteSheet(1));
    }

    public void render(Graphics g){
        g.drawImage(block, x, y, null);

    }

    //it is skipping the broken tile.
    public void collision(){
        if(hp == 1){
            block = null;
        }
        if(hp == 2){
            this.setImage(0, 0);
            hp--;
        }
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
        block = ss.grabImage(x, y, width, height);

    }

}

