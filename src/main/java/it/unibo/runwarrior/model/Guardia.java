package it.unibo.runwarrior.model;

import java.awt.Graphics;

import it.unibo.runwarrior.view.Handler;

public class Guardia extends EnemyImpl {

    public Guardia(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        
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
