package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Snake extends EnemyImpl{
    public BufferedImage rightSnake, rightSnakeMoving, leftSnake, leftSnakeMoving, poisonImage;
    public int minX, maxX;
    public int frameCounter = 0;
    public boolean step = false;

    public boolean dead = false;

    public Snake(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        this.minX = minX;
        this.maxX = maxX;
        setVelocityX(1);
        try {
            rightSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnake.png"));
            rightSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnakeMoving.png"));
            leftSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnake.png"));
            leftSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnakeMoving.png"));
            poisonImage = ImageIO.read(getClass().getResourceAsStream("/Snake/poison.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        BufferedImage currentImage;
        
        if (dead) {
            g.drawImage(poisonImage, x, y, width, height, null);
        } else {
            currentImage = velocityX > 0
                ? (step ? rightSnakeMoving : rightSnake)
                : (step ? leftSnakeMoving : leftSnake);
    
            g.drawImage(currentImage, x, y, width, height, null);
        }

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

    @Override
    public void die(){
        dead = true;
        setVelocityX(0);
    }

}
