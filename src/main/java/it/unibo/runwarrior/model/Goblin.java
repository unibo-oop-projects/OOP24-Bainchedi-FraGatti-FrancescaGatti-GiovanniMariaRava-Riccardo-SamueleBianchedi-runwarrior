package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Goblin extends EnemyImpl{
    public BufferedImage rightGoblin, leftGoblin, rightGoblinMoving, leftGoblinMoving; 
    public int minX, maxX;
    public Goblin(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        try {
            rightGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/rightGoblin.png"));
            leftGoblin = ImageIO.read(getClass().getResourceAsStream("/Goblin/leftGoblin.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.minX = minX;
        this.maxX = maxX;
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
