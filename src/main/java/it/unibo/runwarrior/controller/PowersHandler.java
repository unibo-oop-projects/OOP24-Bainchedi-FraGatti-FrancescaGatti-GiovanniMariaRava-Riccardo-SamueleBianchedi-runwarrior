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
    public List<Character> everyPowerUp = new ArrayList<>();

    public PowersHandler(GameLoopPanel glp, CharacterComand cmd, CollisionDetection coll, HandlerMapElement mapH, PowerUpFactoryImpl pFact){
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd, coll, mapH, pFact),
        new ArmourWarrior(glp, cmd, coll, mapH, pFact),
        new SwordWarrior(glp, cmd, coll, mapH, pFact), 
        new NakedWizard(glp, cmd, coll, mapH, pFact), 
        new ArmourWizard(glp, cmd, coll, mapH, pFact), 
        new StickWizard(glp, cmd, coll, mapH, pFact)));
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
            int realx = glp.getPlayer().getMovementHandler().getPlX();
            int x = glp.getPlayer().getMovementHandler().getScX();
            int y = glp.getPlayer().getMovementHandler().getPlY();
            int shift = glp.getPlayer().getMovementHandler().getGroundX();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, 0);
        }
    }

    public void losePower(boolean enemy){
        this.index--;
        if(index >= 0){
            int realx = glp.getPlayer().getMovementHandler().getPlX();
            int x = glp.getPlayer().getMovementHandler().getScX();
            int y = glp.getPlayer().getMovementHandler().getPlY();
            int shift = glp.getPlayer().getMovementHandler().getGroundX();
            long lastHit = enemy ? glp.getPlayer().getMovementHandler().getKillDetection().getHitWaitTime() 
            : glp.getCollisionDetection().getHitWaitTime();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, lastHit);
        }
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
