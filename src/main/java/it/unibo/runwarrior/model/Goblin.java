package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Goblin extends EnemyImpl{
    public BufferedImage rightGoblin, leftGoblin, rightGoblinMoving, leftGoblinMoving; 
    public int minX, maxX;

    public int frameCounter = 0;
    public boolean step = false;
    public Goblin(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        setVelocityX(2);
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
        BufferedImage currentImage;
        if(velocityX>0){
            currentImage = step ? rightGoblinMoving : rightGoblin;
        }else{
            currentImage = step ? leftGoblinMoving : leftGoblin;
        }
        g.drawImage(currentImage, x, y,width, height, null);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
