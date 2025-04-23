package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.Handler;

public class Wizard extends EnemyImpl {
    public BufferedImage rightWizard, rightWizardMoving, leftWizard, leftWizardMoving; 

    public Wizard(int x, int y, int width, int height, boolean solid, Handler handler) {
        super(x, y, width, height, solid, handler);
        try {
            rightWizard= ImageIO.read(getClass().getResourceAsStream("/Wizard/rightWizard.png"));
            rightWizardMoving= ImageIO.read(getClass().getResourceAsStream("/Wizard/rightWizardMoving.png"));
            leftWizard= ImageIO.read(getClass().getResourceAsStream("/Wizard/leftWizard.png"));
            leftWizardMoving= ImageIO.read(getClass().getResourceAsStream("/Wizard/leftWizardMoving.png")); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(rightWizard, x, y,width,height, null);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
