package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Guard extends EnemyImpl {
    public BufferedImage rightImage, leftImage;
    public int minX, maxX;
    public Guard(int x, int y, int width, int height, boolean solid, Handler handler, int minX, int maxX) {
        super(x, y, width, height, solid, handler);
        setVelocityX(2);
        try{
            rightImage = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuard.png"));
            leftImage = ImageIO.read(getClass().getResourceAsStream("/Guardia/leftGuard.png"));
            image = rightImage;
        }catch (IOException e){
            e.printStackTrace();
        }
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    @Override
    public void update() {
        x += velocityX;
        
        if(x<=minX | x>= maxX -width){
            velocityX = -velocityX;

            if(velocityX>0){
                image=rightImage;
            }else{
                image = leftImage;
            }
        }
    }

}
