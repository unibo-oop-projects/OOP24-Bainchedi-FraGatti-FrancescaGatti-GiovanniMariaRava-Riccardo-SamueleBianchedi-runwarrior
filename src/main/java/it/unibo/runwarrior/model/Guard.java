package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Guard extends EnemyImpl {

    public Guard(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        setVelocityX(2);
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/Guardia/rightGuardia.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
