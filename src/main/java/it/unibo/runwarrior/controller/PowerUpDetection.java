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

    public String checkCollisionWithPowers(Character player, CharacterMovementHandler move){
        powerCollision.addAll(pFact.getPowerUps());
        Rectangle playerArea = player.getArea();
        String dir = "";
        for(PowerUpImpl pUp : powerCollision){
            if(futureArea(playerArea).intersects(pUp.getTouchArea()) && !pUp.getTouchArea().isEmpty()){
                if(isTouchingUp(playerArea, pUp.getTouchArea())){
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

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    public Rectangle futureArea(Rectangle r1) {
        Rectangle futureArea = new Rectangle(r1);
        futureArea.translate(0, CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    public boolean isTouchingUp(Rectangle playerArea, Rectangle pUpArea){
        return playerArea.y + playerArea.height <= pUpArea.y && 
        ((playerArea.x + toll >= pUpArea.x && playerArea.x + toll <= pUpArea.x + pUpArea.width) ||
        (playerArea.x + playerArea.width - toll >= pUpArea.x && playerArea.x + playerArea.width - toll <= pUpArea.x + pUpArea.width));
    }
}
