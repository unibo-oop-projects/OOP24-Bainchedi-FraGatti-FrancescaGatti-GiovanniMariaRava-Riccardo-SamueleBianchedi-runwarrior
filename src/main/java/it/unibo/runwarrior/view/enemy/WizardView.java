package it.unibo.runwarrior.view.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

public class WizardView implements EnemyView {
    private BufferedImage rightWizard;
    private BufferedImage rightWizardMoving;
    private BufferedImage leftWizard; 
    private BufferedImage leftWizardMoving; 
    private GameLoopPanel glp;

    public WizardView(GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g, EnemyImpl enemy) {
        BufferedImage currentImage;

        if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightWizardMoving : rightWizard;
        } else {
            currentImage = enemy.step ? leftWizardMoving : leftWizard;
        }

        int shift = glp.getMapHandler().getShift(); 
        g.drawImage(currentImage, enemy.x + shift, enemy.y, enemy.width, enemy.height, null);
    }

    @Override
    public void loadResources() throws IOException {
        rightWizard= ImageIO.read(getClass().getResourceAsStream("/Wizard/rightWizard.png"));
        rightWizardMoving= ImageIO.read(getClass().getResourceAsStream("/Wizard/rightWizardMoving.png"));
        leftWizard= ImageIO.read(getClass().getResourceAsStream("/Wizard/leftWizard.png"));
        leftWizardMoving= ImageIO.read(getClass().getResourceAsStream("/Wizard/leftWizardMoving.png"));
    }
}
