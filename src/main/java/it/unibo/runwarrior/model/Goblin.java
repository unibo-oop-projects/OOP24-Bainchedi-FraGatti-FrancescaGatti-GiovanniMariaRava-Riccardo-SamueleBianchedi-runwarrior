package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class Goblin extends EnemyImpl{
    public BufferedImage rightGoblin, leftGoblin, rightGoblinMoving, leftGoblinMoving; 
    public int minX, maxX;

    public int frameCounter = 0;
    public boolean step = false;
    public Goblin(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        setVelocityX(2);
        try {
            rightGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/rightGoblin.png"));
            leftGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/leftGoblin.png"));
            rightGoblinMoving = ImageIO.read(getClass().getResourceAsStream("/Goblin/rightGoblinMoving.png"));
            leftGoblinMoving = ImageIO.read(getClass().getResourceAsStream("/Goblin/leftGoblinMoving.png"));
            image = rightGoblin;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage currentImage;
        if(velocityX == 0){
            currentImage = image;
        }else if(velocityX>0){
            currentImage = step ? rightGoblinMoving : rightGoblin;
        }else{
            currentImage = step ? leftGoblinMoving : leftGoblin;
        }
        
        int shift = glp.getMapHandler().getShift();  
        g.drawImage(currentImage, x+shift, y, width, height, null);
        
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
