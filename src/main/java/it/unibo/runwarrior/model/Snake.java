package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class Snake extends EnemyImpl{
    private static final int TIME_AFTER_DEATH = 300;
    public BufferedImage rightSnake, rightSnakeMoving, leftSnake, leftSnakeMoving, poisonImage;
    public int minX, maxX;
    public int frameCounter = 0;
    public boolean step = false;
    public EnemyHandler enemyHandler;
    public boolean dead = false;
    private int deathTimer = 0;
    public Snake(int x, int y, int width, int height, boolean solid, EnemyHandler handler, int minX, int maxX, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        this.minX = minX;
        this.maxX = maxX;
        this.enemyHandler = handler;
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
        if (!dead) {
            x += velocityX;
            if (x <= minX || x >= maxX - width) {
                velocityX = -velocityX;
            }
            frameCounter++;
            if (frameCounter >= 20) {
                step = !step;
                frameCounter = 0;
            }
        } else {
            deathTimer++;
            if (deathTimer > TIME_AFTER_DEATH) {
                enemyHandler.removeEnemy(this);
            }
        }
    }

    @Override
    public void die(){
        dead = true;
        setVelocityX(0);
    }

}
