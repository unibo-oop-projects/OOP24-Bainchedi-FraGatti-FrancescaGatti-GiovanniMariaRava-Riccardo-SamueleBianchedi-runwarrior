package it.unibo.runwarrior.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.controller.EnemyHandler;

public class Wizard extends EnemyImpl {
    private BufferedImage rightWizard;
    private BufferedImage rightWizardMoving;
    private BufferedImage leftWizard; 
    private BufferedImage leftWizardMoving; 

    public Wizard(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        setVelocityX(1);
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
        BufferedImage currentImage;

        if (velocityX > 0) {
            currentImage = step ? rightWizardMoving : rightWizard;
        } else {
            currentImage = step ? leftWizardMoving : leftWizard;
        }

        int shift = glp.getMapHandler().getShift(); 
        g.drawImage(currentImage, x + shift, y, width, height, null);
    }
}
