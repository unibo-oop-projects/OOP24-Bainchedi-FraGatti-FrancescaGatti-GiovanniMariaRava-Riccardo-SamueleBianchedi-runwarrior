package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;

import it.unibo.runwarrior.model.PowerUpImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class PowerUpDetection {
    private GameLoopPanel glp;
    private PowerUpFactoryImpl pFact;
    private ArrayList<PowerUpImpl> powerCollision = new ArrayList<>();
    private int gotPower = 0;
    private long hitWaitTime;

    public PowerUpDetection(GameLoopPanel glp, PowerUpFactoryImpl pUpFact){
        this.glp = glp;
        this.pFact = pUpFact;
        powerCollision.addAll(pFact.getPowerUps());
    }

    public boolean touch(Rectangle r1, Rectangle r2) {
        Rectangle expanded = new Rectangle(r2.x - 1, r2.y - 1, r2.width + 2, r2.height + 2);
        return expanded.intersects(r1);
    }
}
