package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.view.GameLoopPanel;

public class Monkey extends EnemyImpl {
    public BufferedImage rightMonkey, leftMonkey,
                        rightMonkeyMoving, leftMonkeyMoving, 
                        rightMonkeyRunning, leftMonkeyRunning,  
                        banana;

    public int frameCounter = 0;
    public boolean step = false;

    public boolean dead = false;

    public Monkey(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        setVelocityX(2);
        try{
            rightMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkey.png"));
            leftMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkey.png"));
            rightMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyMoving.png"));
            leftMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyMoving.png"));
            rightMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightRunningMonkey.png"));
            leftMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftRunningMonkey.png"));
            banana = ImageIO.read(getClass().getResourceAsStream("/Monkey/banana.png"));
            image = rightMonkey;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        if(!dead){
            
            BufferedImage currentImage;
            if (velocityX == 0){
                currentImage = image;
            }else if (velocityX > 0) {
                currentImage = step ? rightMonkeyMoving : rightMonkeyRunning;
                image = rightMonkey;
            } else {
                currentImage = step ? leftMonkeyMoving : leftMonkeyRunning;
                image = leftMonkey;
            }
            
            int cameraX = glp.getPlayer().getArea().x;
            int screenX = x - cameraX;  
            g.drawImage(currentImage, screenX, y, width, height, null);
        }else{

            g.drawImage(banana, x, y, width, height, glp);
        }
        
    }

    @Override
    public void update() {
        x += velocityX;

        checkMapCollision(glp.getMapHandler().getCollisionRectangles());
        
        frameCounter++;
        if (frameCounter >= 20) { 
            step = !step;
            frameCounter = 0;
        }
    }

}
