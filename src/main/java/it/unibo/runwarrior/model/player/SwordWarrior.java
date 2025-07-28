package it.unibo.runwarrior.model.player;

import java.awt.Rectangle;
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
            setRight0(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/stopRightS.png")));
            setRight1(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goRightS1.png")));
            setRight2(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goRightS2.png")));
            setLeft0(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/stopLeftS.png")));
            setLeft1(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goLeftS1.png")));
            setLeft2(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/goLeftS2.png")));
            setJumpR(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/jumpRightS.png")));
            setJumpL(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/jumpLeftS.png")));
            setAttackR(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/attackRight.png")));
            setAttackL(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/attackLeft.png")));
            setTipR(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/tipRight.png")));
            setTipL(ImageIO.read(SwordWarrior.class.getResourceAsStream("/WarriorImages/tipLeft.png")));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateAttackCollision() {
        if (getAnimationHandler().getFrame() == PlayerFrame.ATTACK_FRAME && isRightDirection()) {
            setSwordArea(new Rectangle(getMovementHandler().getPlX() + getSizeCharacter(), 
            getMovementHandler().getPlY() + getSizeCharacter() / 4,
            getSizeCharacter(), 
            getSizeCharacter() - (getSizeCharacter() / 4) - (TO_TOUCH_FLOOR * 2)));
        }
        if (getAnimationHandler().getFrame() == PlayerFrame.ATTACK_FRAME && !isRightDirection()) {
            setSwordArea(new Rectangle(getMovementHandler().getPlX() - getSizeCharacter(), 
            getMovementHandler().getPlY() + getSizeCharacter() / 4,
            getSizeCharacter(), 
            getSizeCharacter() - (getSizeCharacter() / 4) - (TO_TOUCH_FLOOR * 2)));
        }
    }
}
