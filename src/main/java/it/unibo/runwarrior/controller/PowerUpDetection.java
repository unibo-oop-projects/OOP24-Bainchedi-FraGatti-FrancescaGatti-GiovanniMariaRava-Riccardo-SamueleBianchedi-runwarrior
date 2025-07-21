package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.PowerUpImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class PowerUpDetection {
    private GameLoopPanel glp;
    private PowerUpFactoryImpl pFact;
    private ArrayList<PowerUpImpl> powerCollision = new ArrayList<>();
    private long hitWaitTime;
    private int toll = 5;

    public PowerUpDetection(GameLoopPanel glp, PowerUpFactoryImpl pUpFact){
        this.glp = glp;
        this.pFact = pUpFact;
    }

    public String checkCollisionWithPowers(Character player, CharacterMovementHandlerImpl move){
        powerCollision.addAll(pFact.getPowerUps());
        Rectangle playerArea = player.getArea();
        String dir = "";
        for(PowerUpImpl pUp : powerCollision){
            if(touch(playerArea, pUp.getTouchArea()) && !pUp.getTouchArea().isEmpty()){
                if(playerArea.y + playerArea.height == pUp.getTouchArea().y &&
                    ((playerArea.x + toll >= pUp.getTouchArea().x && playerArea.x + toll <= pUp.getTouchArea().x + pUp.getTouchArea().width) ||
                     (playerArea.x + playerArea.width - toll >= pUp.getTouchArea().x && playerArea.x + playerArea.width - toll <= pUp.getTouchArea().x + pUp.getTouchArea().width))){
                    dir = "up";
                    if(pUp.isEggOpen() && !pUp.isPowerTaken() && System.currentTimeMillis() - hitWaitTime > 200){
                        glp.getPowersHandler().setPowers();
                        pUp.takePower();
                    }
                    else if(!pUp.isEggOpen()){
                        move.setJumpKill();
                        pUp.openTheEgg();
                        hitWaitTime = System.currentTimeMillis();
                    }
                }
                else if((playerArea.x + playerArea.width >= pUp.getTouchArea().x && playerArea.x < pUp.getTouchArea().x)){
                    dir = "right";
                    if(pUp.isEggOpen() && !pUp.isPowerTaken()){
                        glp.getPowersHandler().setPowers();
                        pUp.takePower();
                    }
                }
                else if(playerArea.x <= pUp.getTouchArea().x + pUp.getTouchArea().width){
                    dir = "left";
                    if(pUp.isEggOpen() && !pUp.isPowerTaken()){
                        glp.getPowersHandler().setPowers();
                        pUp.takePower();
                    }
                }
            }
        }
        return dir;
    }

    public boolean touch(Rectangle r1, Rectangle r2) {
        Rectangle expanded = new Rectangle(r2.x - 1, r2.y - 1, r2.width + 1, r2.height + 1);
        return expanded.intersects(r1);
    }
}
