package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class Snake extends EnemyImpl{
    public BufferedImage rightSnake, rightSnakeMoving, leftSnake, leftSnakeMoving, poisonImage;
    public int frameCounter = 0;
    public boolean step = false;
    public EnemyHandler enemyHandler;
    public Snake(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
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

        if (velocityX > 0) {
            currentImage = step ? rightSnakeMoving : rightSnake;
        } else {
            currentImage = step ? leftSnakeMoving : leftSnake;
        }

        int shift = glp.getMapHandler().getShift(); 
        g.drawImage(currentImage, x + shift , y, width, height, null);

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
