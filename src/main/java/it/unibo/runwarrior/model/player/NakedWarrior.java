package it.unibo.runwarrior.model.player;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Implementation of the warrior with cape and stick.
 */
public class NakedWarrior extends CharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param panel game-loop panel
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pMan object that prints powerups
     */
    public NakedWarrior(final GameLoopPanel panel, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpManager pMan) {
        super(panel, commands, mapHandler, pMan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopRightN.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopLeftN.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN2.png"));
            jumpR = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/jumpRightN.png"));
            jumpL = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/jumpLeftN.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
