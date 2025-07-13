package it.unibo.runwarrior.controller;

import java.util.ArrayList;
import java.util.Arrays;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.ArmourWarrior;
import it.unibo.runwarrior.model.NakedWarrior;
import it.unibo.runwarrior.model.SwordWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class PowersHandler {
    private GameLoopPanel glp;
    private int index = 0;
    public ArrayList<Character> everyPowerUp = new ArrayList<>();

    public PowersHandler(GameLoopPanel glp, CharacterComand cmd, CollisionDetection coll, HandlerMapElement mapH, PowerUpFactoryImpl pFact){
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd, coll, mapH, pFact),
         new ArmourWarrior(glp, cmd, coll, mapH, pFact),
         new SwordWarrior(glp, cmd, coll, mapH, pFact)));
    }

    public void setPowers(){
        this.index++;
        int realx = glp.getPlayer().getMovementHandler().getPlX();
        int x = glp.getPlayer().getMovementHandler().getScX();
        int y = glp.getPlayer().getMovementHandler().getPlY();
        int shift = glp.getPlayer(). getMovementHandler().getGroundX();
        glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift);
    }

    public int getPowers(){
        return this.index;
    }
}
