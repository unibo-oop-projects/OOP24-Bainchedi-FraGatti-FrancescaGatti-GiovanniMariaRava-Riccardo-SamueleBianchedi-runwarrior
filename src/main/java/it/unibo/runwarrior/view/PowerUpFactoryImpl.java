package it.unibo.runwarrior.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.model.PowerUpImpl;

public class PowerUpFactoryImpl {
    
    private ArrayList<PowerUpImpl> powerUps = new ArrayList<>();
    private HandlerMapElement mapHandler;
    private int tileSize;
    private int distance;

    public PowerUpFactoryImpl(GameLoopPanel glp, HandlerMapElement hM, int[][] map){
        this.mapHandler = hM;
        this.tileSize = hM.getTileSize();
        distance = 55;
        for(int i = 0; i < 3; i++){
            PowerUpImpl p = new PowerUpImpl(glp);
            int row = 0;
            while(map[row][distance] != 1){
                row++;
            }
            p.getTouchArea().setBounds(distance*tileSize, (row-1)*tileSize, tileSize, tileSize);
            powerUps.add(p);
            distance = distance + 25;
        }
    }

    public void PowerUpAppearance(PowerUpImpl pow){
        this.powerUps.add(pow);
    }

    public void printPowerUp(Graphics2D gr2){
        BufferedImage im;
        for(PowerUpImpl p : powerUps){
            p.powerUpImage();
            if(!p.isEggOpen()){
                im = p.getEgg();
            }
            else{
                im = p.getImage();
            }
            if(!p.isPowerTaken()){
                gr2.drawImage(im, p.getTouchArea().x + mapHandler.getShift(), p.getTouchArea().y, tileSize, p.getTouchArea().height, null);
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
