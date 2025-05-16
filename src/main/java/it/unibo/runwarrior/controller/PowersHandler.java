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

    public PowersHandler(GameLoopPanel glp, CharacterComand cmd){
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd), new ArmourWarrior(glp, cmd), new SwordWarrior(glp, cmd)));
    }

    public void setPowers(){
        this.index++;
        glp.setPlayer(everyPowerUp.get(index));
    }

    public int getPowers(){
        return this.index;
    }
}
