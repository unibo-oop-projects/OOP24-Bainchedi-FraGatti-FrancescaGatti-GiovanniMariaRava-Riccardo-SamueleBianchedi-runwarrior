package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;

import it.unibo.runwarrior.model.Character;
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

    public String checkCollisionWithPowers(Character player, CharacterMovementHandler move){
        Rectangle playerArea = player.getArea();
        String dir = "";
        for(PowerUpImpl pUp : powerCollision){
            if(touch(playerArea, pUp.getTouchArea())){
                if(playerArea.y == pUp.getTouchArea().y &&
                    (playerArea.x >= pUp.getTouchArea().x && playerArea.x <= pUp.getTouchArea().y + pUp.getTouchArea().width)){
                    dir = "up";
                    if(pUp.isEggOpen() && !pUp.isPowerTaken() && System.currentTimeMillis() - hitWaitTime > 200){
                        glp.getPowersHandler().setPowers();
                        gotPower++;
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
                        gotPower++;
                        pUp.takePower();
                    }
                }
                else if(playerArea.x <= pUp.getTouchArea().x + pUp.getTouchArea().width){
                    dir = "left";
                    if(pUp.isEggOpen() && !pUp.isPowerTaken()){
                        glp.getPowersHandler().setPowers();
                        gotPower++;
                        pUp.takePower();
                    }
                }
            }
        }
        return dir;
    }

    public boolean touch(Rectangle r1, Rectangle r2) {
        Rectangle expanded = new Rectangle(r2.x - 1, r2.y - 1, r2.width + 2, r2.height + 2);
        return expanded.intersects(r1);
    }
}
