package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Guard extends EnemyImpl {
    public BufferedImage rightGuard, leftGuard, rightGuardMoving, leftGuardMoving;
    public int minX, maxX;

    public int frameCounter = 0;
    public boolean step = false;

    public Guard(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        setVelocityX(2);
        try{
            rightGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuard.png"));
            leftGuard = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuard.png"));
            rightGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuardMoving.png"));
            leftGuardMoving = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuardMoving.png"));
            image = rightGuard;
        }catch (IOException e){
            e.printStackTrace();
        }
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void render(Graphics g) {
        BufferedImage currentImage;

        if (velocityX > 0) {
            currentImage = step ? rightGuardMoving : rightGuard;
        } else {
            currentImage = step ? leftGuardMoving : leftGuard;
        }

        g.drawImage(currentImage, x, y, width, height, null);
    }

    @Override
    public void update() {
        x += velocityX;

        // Cambia direzione se arriva ai limiti
        if (x <= minX || x >= maxX - width) {
            velocityX = -velocityX;
        }

        // Gestione "passo" animazione
        frameCounter++;
        if (frameCounter >= 20) { // cambia ogni 20 tick
            step = !step;
            frameCounter = 0;
        }
    }

}
