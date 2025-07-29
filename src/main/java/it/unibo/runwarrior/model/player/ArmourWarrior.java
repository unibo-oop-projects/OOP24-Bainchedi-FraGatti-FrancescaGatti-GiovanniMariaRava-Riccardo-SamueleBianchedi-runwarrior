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
 * Implementation of the warrioir with armour.
 */
public class ArmourWarrior extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public ArmourWarrior(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            setRight0(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopRightA.png")));
            setRight1(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goRightA1.png")));
            setRight2(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goRightA2.png")));
            setLeft0(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopLeftA.png")));
            setLeft1(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goLeftA1.png")));
            setLeft2(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/goLeftA2.png")));
            setJumpR(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/jumpRightA.png")));
            setJumpL(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftA.png")));
            setAttackR(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopRightA.png")));
            setAttackL(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WarriorImages/stopLeftA.png")));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
