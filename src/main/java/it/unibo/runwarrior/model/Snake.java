package it.unibo.runwarrior.model;

import java.awt.Graphics;

import it.unibo.runwarrior.view.Handler;

public class Snake extends EnemyImpl{

    public Snake(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        //TODO Auto-generated constructor stub
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
