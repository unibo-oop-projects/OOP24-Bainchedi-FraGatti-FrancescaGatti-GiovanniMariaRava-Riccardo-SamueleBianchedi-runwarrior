package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Implementation of the warrior with cape and stick.
 */
public class NakedWarrior extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pMan object that prints powerups
     */
    public NakedWarrior(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpManager pMan) {
        super(glc, commands, mapHandler, pMan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopRightN.png"));
            right1 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goRightN1.png"));
            right2 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goRightN2.png"));
            left0 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopLeftN.png"));
            left1 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goLeftN1.png"));
            left2 = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goLeftN2.png"));
            jumpR = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/jumpRightN.png"));
            jumpL = ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftN.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
