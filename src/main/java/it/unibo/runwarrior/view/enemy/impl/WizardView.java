package it.unibo.runwarrior.view.enemy.impl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;
/**
 * Implementation of the enemy view with Wizard enemy.
 */
public class WizardView implements EnemyView {
    private BufferedImage rightWizard;
    private BufferedImage rightWizardMoving;
    private BufferedImage leftWizard; 
    private BufferedImage leftWizardMoving; 
    private final GameLoopPanel glp;

    /**
     * Constructor of the class WizardView.
     * @param glp is the panel in which the guard need to be rendered
     */

    public WizardView(final GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void render(final Graphics g, final EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.getVelocityX() > 0) {
            currentImage = enemy.isStep() ? rightWizardMoving : rightWizard;
        } else {
            currentImage = enemy.isStep() ? leftWizardMoving : leftWizard;
        }
        final int shift = glp.getMapHandler().getShift(); 
        g.drawImage(currentImage, enemy.getX() + shift, enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void loadResources() throws IOException {
        rightWizard = ImageIO.read(WizardView.class.getResourceAsStream("/Wizard/rightWizard.png"));
        rightWizardMoving = ImageIO.read(WizardView.class.getResourceAsStream("/Wizard/rightWizardMoving.png"));
        leftWizard = ImageIO.read(WizardView.class.getResourceAsStream("/Wizard/leftWizard.png"));
        leftWizardMoving = ImageIO.read(WizardView.class.getResourceAsStream("/Wizard/leftWizardMoving.png"));
    }
}
