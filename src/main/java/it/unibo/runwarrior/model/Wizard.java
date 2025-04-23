package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.view.Handler;

public class Wizard extends EnemyImpl {
    public BufferedImage rightWizard, rightWizardMving, leftWizard, leftWizardMoving; 

    public Wizard(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        try {

            
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
