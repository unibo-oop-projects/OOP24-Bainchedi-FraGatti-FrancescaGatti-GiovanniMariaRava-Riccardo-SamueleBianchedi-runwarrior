package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Implementation of the warrioir with armour.
 */
public class ArmourWarrior extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param panel game-loop panel
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pMan object that prints powerups
     */
    public ArmourWarrior(final GameLoopPanel panel, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpManager pMan) {
        super(panel, commands, mapHandler, pMan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopRightA.png"));
            right1 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goRightA1.png"));
            right2 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goRightA2.png"));
            left0 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopLeftA.png"));
            left1 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goLeftA1.png"));
            left2 = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goLeftA2.png"));
            jumpR = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/jumpRightA.png"));
            jumpL = ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftA.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
