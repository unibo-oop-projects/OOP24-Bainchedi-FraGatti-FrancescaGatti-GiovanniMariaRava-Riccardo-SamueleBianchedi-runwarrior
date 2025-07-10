package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class Guard extends EnemyImpl {
    public BufferedImage rightGuard, leftGuard, rightGuardMoving, leftGuardMoving, rightGuardRunning, leftGuardRunning;
    public int minX, maxX;

    public int frameCounter = 0;
    public boolean step = false;

    public Guard(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        setVelocityX(2);
        try{
            rightGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuard.png"));
            leftGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuard.png"));
            rightGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuardMoving.png"));
            leftGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuardMoving.png"));
            rightGuardRunning = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightRunningGuard.png"));
            leftGuardRunning = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftRunningGuard.png"));
            image = rightGuard;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage currentImage;
        if (velocityX == 0){
            currentImage = image;
        }else if (velocityX > 0) {
            currentImage = step ? rightGuardMoving : rightGuardRunning;
            image = rightGuard;
        } else {
            currentImage = step ? leftGuardMoving : leftGuardRunning;
            image = leftGuard;
        }
        //int drawX = x+glp.getCameraShift();
        g.drawImage(currentImage, x, y, width, height, null);
    }

    @Override
    public void update() {
        x += velocityX;

        //checkMapCollision(glp.getMapHandler().getCollisionRectagles());
        
        frameCounter++;
        if (frameCounter >= 20) { 
            step = !step;
            frameCounter = 0;
        }
    }

}
