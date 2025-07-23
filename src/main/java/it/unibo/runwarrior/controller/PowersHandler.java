package it.unibo.runwarrior.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.ArmourWarrior;
import it.unibo.runwarrior.model.player.ArmourWizard;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.model.player.NakedWizard;
import it.unibo.runwarrior.model.player.StickWizard;
import it.unibo.runwarrior.model.player.SwordWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class PowersHandler {
    private GameLoopPanel glp;
    private int index;
    private int maxIndex;
    private List<Character> everyPowerUp = new ArrayList<>();
    private int realx;
    private int x;
    private int y;
    private int shift;

    public PowersHandler(GameLoopPanel glp, CharacterComand cmd, HandlerMapElement mapH, PowerUpFactoryImpl pFact){
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd, mapH, pFact),
        new ArmourWarrior(glp, cmd, mapH, pFact),
        new SwordWarrior(glp, cmd, mapH, pFact), 
        new NakedWizard(glp, cmd, mapH, pFact), 
        new ArmourWizard(glp, cmd, mapH, pFact), 
        new StickWizard(glp, cmd, mapH, pFact)));
    }

    public void setIndex(){
        if(glp.getPlayer().getClass().equals(NakedWarrior.class)){
            index = 0;
            maxIndex = 2;
        }
        if(glp.getPlayer().getClass().equals(NakedWizard.class)){
            index = 3;
            maxIndex = 5;
        }
    }

    public void setPowers(){
        if(index < maxIndex && index >= 0){
            this.index++;
            setPosition();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, 0);
        }
    }

    public void losePower(boolean enemy){
        this.index--;
        if(index >= 0){
            setPosition();
            long lastHit = enemy ? glp.getPlayer().getMovementHandler().getKillDetection().getHitWaitTime() 
            : glp.getPlayer().getMovementHandler().getCollisionDetection().getHitWaitTime();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, lastHit);
        }
    }

    private void setPosition(){
        realx = glp.getPlayer().getMovementHandler().getPlX();
        x = glp.getPlayer().getMovementHandler().getScX();
        y = glp.getPlayer().getMovementHandler().getPlY();
        shift = glp.getPlayer().getMovementHandler().getGroundX();
    }

    public int getPowers(){
        return this.index;
    }

    public boolean gameOver(){
        if (maxIndex == 2 && index < 0) {
            return true;
        }
        if (maxIndex == 5 && index < 3) {
            return true;
        }
        return false;
    }
}
