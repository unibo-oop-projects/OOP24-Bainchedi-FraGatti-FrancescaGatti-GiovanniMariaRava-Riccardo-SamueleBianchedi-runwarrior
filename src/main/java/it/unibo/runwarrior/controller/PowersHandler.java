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
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Class that handle the change of powerup during the game.
 */
public class PowersHandler {
    private GameLoopController glp;
    private int index;
    private int maxIndex;
    private List<Character> everyPowerUp = new ArrayList<>();
    private int realx;
    private int x;
    private int y;
    private int shift;

    /**
     * Constructor of the object that handles powerup chenging.
     *
     * @param glp game-loop panel
     * @param cmd object that handles keyboard input
     * @param mapH objects that prints map
     * @param pMan object that prints powerups
     */
    public PowersHandler(final GameLoopController glp, final CharacterComand cmd, final HandlerMapElement mapH, final PowerUpManager pMan) {
        this.glp = glp;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glp, cmd, mapH, pMan),
        new ArmourWarrior(glp, cmd, mapH, pMan),
        new SwordWarrior(glp, cmd, mapH, pMan), 
        new NakedWizard(glp, cmd, mapH, pMan), 
        new ArmourWizard(glp, cmd, mapH, pMan), 
        new StickWizard(glp, cmd, mapH, pMan)));
    }

    /**
     * Set the index of the list based on the skin, warrior or wizard.
     */
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

    /**
     * The player gains a powerup, so it's created a new object from everyPowerUp list.
     */
    public void setPowers(){
        if(index < maxIndex && index >= 0){
            this.index++;
            setPosition();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, 0);
        }
    }

    /**
     * The player loses a powerup, so it's created a new object from everyPowerUp list.
     *
     * @param enemy true if the hit comes from a enemy
     */
    public void losePower(final boolean enemy){
        this.index--;
        if(index >= 0){
            setPosition();
            final long lastHit = enemy ? glp.getPlayer().getMovementHandler().getKillDetection().getHitWaitTime() 
            : glp.getPlayer().getMovementHandler().getCollisionDetection().getHitWaitTime();
            glp.setPlayer(everyPowerUp.get(index), realx, x, y, shift, lastHit);
        }
    }

    /**
     * Set the player position when he gains or loses a powerup.
     */
    private void setPosition(){
        realx = glp.getPlayer().getMovementHandler().getPlX();
        x = glp.getPlayer().getMovementHandler().getScX();
        y = glp.getPlayer().getMovementHandler().getPlY();
        shift = glp.getPlayer().getMovementHandler().getGroundX();
    }

    /**
     * @return the current skin/powerup
     */
    public int getPowers(){
        return this.index;
    }

    /**
     * @return true if the player is dead
     */
    public boolean gameOver(){
        if (maxIndex == 2 && index < 0) {
            return true;
        }
        return maxIndex == 5 && index < 3;
    }
}
