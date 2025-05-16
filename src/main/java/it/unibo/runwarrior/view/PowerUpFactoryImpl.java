package it.unibo.runwarrior.view;

import java.awt.Graphics2D;
import java.util.ArrayList;

import it.unibo.runwarrior.model.PowerUpImpl;

public class PowerUpFactoryImpl {
    
    private ArrayList<PowerUpImpl> powerUps = new ArrayList<>();
    public GameLoopPanel glp;

    public PowerUpFactoryImpl(GameLoopPanel glp){
        this.glp = glp;
    }

    public void PowerUpAppearance(PowerUpImpl pow){
        this.powerUps.add(pow);
    }

    public void printPowerUp(Graphics2D gr){
        for(PowerUpImpl p : powerUps){
            if(!p.getPower()){
                gr.drawImage(p.getImage(), p.getTouchArea().x, 432, 48, 48, null);
            }
            else{
                p.getTouchArea().setSize(0,0);
            }
        }
    }

    public ArrayList<PowerUpImpl> getPowerUps(){
        return powerUps;
    }
}
