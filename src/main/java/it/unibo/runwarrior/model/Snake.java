package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Snake extends EnemyImpl{
    public BufferedImage rightSnake, rightSnakeMoving, leftSnake, leftSnakeMoving;
    public int minX, maxX;
    public int frameCounter = 0;
    public boolean step = false;

    public Snake(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        this.minX = minX;
        this.maxX = maxX;
        setVelocityX(3);
        try {
            rightSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnake.png"));
            rightSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnakeMoving.png"));
            leftSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnake.png"));
            leftSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnakeMoving.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage currentImage;

        if (velocityX > 0) {
            currentImage = step ? rightSnakeMoving : rightSnake;
        } else {
            currentImage = step ? leftSnakeMoving : leftSnake;
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
