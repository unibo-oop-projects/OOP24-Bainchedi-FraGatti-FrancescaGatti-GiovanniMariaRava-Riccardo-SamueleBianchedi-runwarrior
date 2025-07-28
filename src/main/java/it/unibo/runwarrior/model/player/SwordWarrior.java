package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;

/**
 * Implementation of the warrior with armour and sword.
 */
public class SwordWarrior extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public SwordWarrior(final GameLoopController glc, final CharacterComand commands, 
    final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/stopRightS.png"));
            right1 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goRightS1.png"));
            right2 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goRightS2.png"));
            left0 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/stopLeftS.png"));
            left1 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goLeftS1.png"));
            left2 = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goLeftS2.png"));
            jumpR = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/jumpRightS.png"));
            jumpL = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftS.png"));
            attackR = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/attackRight.png"));
            attackL = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/attackLeft.png"));
            tipR = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/tipRight.png"));
            tipL = ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/tipLeft.png"));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateAttackCollision() {
        if (animation.getFrame() == PlayerFrame.ATTACK_FRAME && rightDirection) {
            swordArea.setBounds(movement.getPlX() + getSizeCharacter(), movement.getPlY() + getSizeCharacter() / 4,
            getSizeCharacter(), getSizeCharacter() - (getSizeCharacter() / 4) - (TO_TOUCH_FLOOR * 2));
        }
        if (animation.getFrame() == PlayerFrame.ATTACK_FRAME && !rightDirection) {
            swordArea.setBounds(movement.getPlX() - getSizeCharacter(), movement.getPlY() + getSizeCharacter() / 4,
            getSizeCharacter(), getSizeCharacter() - (getSizeCharacter() / 4) - (TO_TOUCH_FLOOR * 2));
        }
    }
}
