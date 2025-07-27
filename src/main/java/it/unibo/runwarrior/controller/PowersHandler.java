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

/**
 * Class that handle the change of powerup during the game.
 */
public class PowersHandler {
    public static final int MAX_IND = 5;
    private GameLoopController glc;
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
     * @param glc game-loop controller
     * @param cmd object that handles keyboard input
     * @param mapH objects that prints map
     * @param pCon object that creates powerup list
     */
    public PowersHandler(final GameLoopController glc, final CharacterComand cmd, 
    final HandlerMapElement mapH, final PowerUpController pCon) {
        this.glc = glc;
        everyPowerUp.addAll(Arrays.asList(new NakedWarrior(glc, cmd, mapH, pCon),
        new ArmourWarrior(glc, cmd, mapH, pCon),
        new SwordWarrior(glc, cmd, mapH, pCon), 
        new NakedWizard(glc, cmd, mapH, pCon), 
        new ArmourWizard(glc, cmd, mapH, pCon), 
        new StickWizard(glc, cmd, mapH, pCon)));
    }

    /**
     * Set the index of the list based on the skin, warrior or wizard.
     */
    public void setIndex() {
        if (glc.getPlayer().getClass().equals(NakedWarrior.class)) {
            index = 0;
            maxIndex = 2;
        }
        if (glc.getPlayer().getClass().equals(NakedWizard.class)) {
            index = 3;
            maxIndex = MAX_IND;
        }
    }

    /**
     * The player gains a powerup, so it's created a new object from everyPowerUp list.
     */
    public void setPowers() {
        if (index < maxIndex && index >= 0) {
            this.index++;
            setPosition();
            glc.setPlayer(everyPowerUp.get(index), realx, x, y, shift, 0);
        }
    }

    /**
     * The player loses a powerup, so it's created a new object from everyPowerUp list.
     *
     * @param enemy true if the hit comes from a enemy
     */
    public void losePower(final boolean enemy) {
        this.index--;
        if (index >= 0) {
            setPosition();
            final long lastHit = enemy ? glc.getPlayer().getMovementHandler().getKillDetection().getHitWaitTime() 
            : glc.getPlayer().getMovementHandler().getCollisionDetection().getHitWaitTime();
            glc.setPlayer(everyPowerUp.get(index), realx, x, y, shift, lastHit);
        }
    }

    /**
     * Set the player position when he gains or loses a powerup.
     */
    private void setPosition() {
        realx = glc.getPlayer().getMovementHandler().getPlX();
        x = glc.getPlayer().getMovementHandler().getScX();
        y = glc.getPlayer().getMovementHandler().getPlY();
        shift = glc.getPlayer().getMovementHandler().getGroundX();
    }

    /**
     * @return the current skin/powerup
     */
    public int getPowers() {
        return this.index;
    }

    /**
     * @return true if the player is dead
     */
    public boolean gameOver() {
        if (maxIndex == 2 && index < 0) {
            return true;
        }
        return maxIndex == MAX_IND && index < 3;
    }
}
