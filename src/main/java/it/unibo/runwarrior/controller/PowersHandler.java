package it.unibo.runwarrior.controller;

import java.util.ArrayList;
import java.util.Arrays;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.ArmourWarrior;
import it.unibo.runwarrior.model.NakedWarrior;
import it.unibo.runwarrior.model.SwordWarrior;
import it.unibo.runwarrior.view.GameLoopPanel;

public class PowersHandler {
    private GameLoopPanel glp;
    private int index = 0;
    public ArrayList<Character> everyPowerUp = new ArrayList<>();

    public PowersHandler(GameLoopPanel glp, CharacterComand cmd, CollisionDetection coll, HandlerMapElement mapH){
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd, coll, mapH),
         new ArmourWarrior(glp, cmd, coll, mapH),
         new SwordWarrior(glp, cmd, coll, mapH)));
    }

    public void setPowers(){
        this.index++;
        glp.setPlayer(everyPowerUp.get(index));
    }

    public int getPowers(){
        return this.index;
    }
}
