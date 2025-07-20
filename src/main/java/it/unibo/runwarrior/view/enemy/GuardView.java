package it.unibo.runwarrior.view.enemy;

import java.io.IOException;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.view.GameLoopPanel;


public class GuardView {
    private BufferedImage rightGuard;
    private BufferedImage leftGuard;
    private BufferedImage rightGuardMoving;
    private BufferedImage leftGuardMoving;
    private BufferedImage rightGuardRunning;
    private BufferedImage leftGuardRunning;
    private BufferedImage image;
    private GameLoopPanel glp;
    public GuardView(GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
