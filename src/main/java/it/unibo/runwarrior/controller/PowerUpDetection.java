package it.unibo.runwarrior.controller;

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
}
