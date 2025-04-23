package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Snake extends EnemyImpl{
    public BufferedImage rightSnake, rightSnakeMoving, leftSnake, leftSnakeMoving;
    public int minX, maxX;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
