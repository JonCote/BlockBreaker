import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.Color;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 480;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public final String TITLE = "Block Breaker";
    public Color white = new Color(0, 0, 0);
    

    private boolean running = false;
    private boolean ballInPlay;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage lifeSpriteSheet = null;
    private Block[] blockArr;
    private int blockCount;
    private Rectangle deathBox;
    private int life;
    

    private Player p;
    private Ball b;
    private Lives l;
    

    private void init(){
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            lifeSpriteSheet = loader.loadImage("/images/Breakout Tile Set Free/Sprite Sheet/lifeSprite.png");
            spriteSheet = loader.loadImage("/images/Breakout Tile Set Free/Sprite Sheet/breakout_tile_free_scaleDownby4.png");
            
             
        }catch(IOException e){
            e.printStackTrace();
        }

        addKeyListener(new KeyInput(this));
        ballInPlay = false;
        life = 3;
        
        blockCount = 21;
        blockArr = new Block[blockCount];

        int x = 200;
        int y = 100;
        int z = 5;
        for(int i=0; i<blockArr.length; i++){
            blockArr[i] = new Block(x, y, 96, 32, this);
            blockArr[i].setImage(193, 97);

            if(i%z == 0 && i != 0){
                y += 32;
            }
            
            if(x == 584){
                x = 200;
            }else{
                x += 96;
            }    
        }

        p = new Player(434, 695, 86, 16, this);
        b = new Ball(470, 655, 16, 16, this);
        l = new Lives(10, 10, 122, 32, this);


        deathBox = new Rectangle(0, HEIGHT * SCALE + 100, WIDTH * SCALE, 50);
        
    }


    private synchronized void start(){
        if(running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop(){
        if(!running){
            return;
        }
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.exit(1);      
    }

    public void run(){
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if(delta >= 1){
                if(ballInPlay){
                    tick();
                }
                
                updates++;
                delta--;
            }

            /****************** COLLISION DETECTION STARTS HERE **************************/

            Rectangle pRec = new Rectangle(p.getX(), p.getY() - 5, p.getWidth() + 10, 10);
            Rectangle bRec = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
            

            if(bRec.intersects(pRec)) {
                b.collision();
            }

            if(bRec.intersects(deathBox)){
                life--;
                if(life == 0){
                    init();
                }else{
                    ballInPlay = false;
                    p.resetPos();
                    b.resetPos();
                    if(life == 2){
                        l.setImage(122, 0);
                    }
                    if(life == 1){
                        l.setImage(244, 0);
                    }
                    
                }
                
            }

            for(int i = 0; i < blockArr.length; i++){
                if(blockArr[i] != null){
                    Rectangle blckRec = new Rectangle(blockArr[i].getX(), blockArr[i].getY(), blockArr[i].getWidth(), blockArr[i].getHeight());
                
                    if(bRec.intersects(blckRec)){
                        b.collision();
                        blockArr[i].collision();
                        blckRec = null;
                        blockArr[i] = null;
                        
                    }
                }
            }

            /****************** COLLISION DETECTION ENDS HERE **************************/

            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }

        }
        stop();
    }


    private void tick(){
        p.tick();
        b.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        
        /************* DRAWING LOCATION STARTS HERE ****************/

        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        p.render(g);
        b.render(g);
        l.render(g);

        
      
        for(int i=0; i<blockArr.length; i++){
            if(blockArr[i] != null){
                blockArr[i].render(g);
            }   
        }

        
        /************* DRAWING LOCATION ENDS HERE ****************/

        g.dispose();
        bs.show();

        

    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            p.setVelX(6);
        } else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            p.setVelX(-6);
        }

        if(key == KeyEvent.VK_SPACE){
            ballInPlay = true;
        }

    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            p.setVelX(0);
        } else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            p.setVelX(0);
        }

    }


    public static void main(String args[]){

        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();

    }

    public BufferedImage getSpriteSheet(int x){
        if(x == 1){
            return spriteSheet;
        }
        if(x == 2){
            return lifeSpriteSheet;
        }
        return null;
        
    }

}
