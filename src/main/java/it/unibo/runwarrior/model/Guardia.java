package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Guardia extends EnemyImpl {

    public Guardia(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        try{
            image = ImageIO.read(getClass().getResourceAsStream(""));
        }catch (IOException e){
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
