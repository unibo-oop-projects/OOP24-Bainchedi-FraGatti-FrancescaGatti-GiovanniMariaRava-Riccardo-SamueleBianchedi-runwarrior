package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;
import it.unibo.runwarrior.controller.player.CharacterComand;
import it.unibo.runwarrior.model.player.impl.AbstractCharacterImpl;

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
     * @param pCon object that creates powerup list
     */
    public NakedWarrior(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            setRight0(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopRightN.png")));
            setRight1(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goRightN1.png")));
            setRight2(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goRightN2.png")));
            setLeft0(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopLeftN.png")));
            setLeft1(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goLeftN1.png")));
            setLeft2(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/goLeftN2.png")));
            setJumpR(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/jumpRightN.png")));
            setJumpL(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftN.png")));
            setAttackR(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopRightN.png")));
            setAttackL(ImageIO.read(NakedWarrior.class.getResourceAsStream("/WarriorImages/stopLeftN.png")));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
