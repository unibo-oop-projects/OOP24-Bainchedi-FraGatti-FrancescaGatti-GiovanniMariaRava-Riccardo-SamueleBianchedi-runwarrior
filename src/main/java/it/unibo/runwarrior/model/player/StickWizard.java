package it.unibo.runwarrior.model.player;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;
import it.unibo.runwarrior.controller.player.CharacterComand;

/**
 * Implementation of the wizard with cape and stick.
 */
public class StickWizard extends AbstractCharacterImpl {
    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public StickWizard(final GameLoopController glc, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            setRight0(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/stopRightSW.png")));
            setRight1(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goRightSW1.png")));
            setRight2(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goRightSW2.png")));
            setLeft0(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/stopLeftSW.png")));
            setLeft1(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goLeftSW1.png")));
            setLeft2(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goLeftSW2.png")));
            setJumpR(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/jumpRightSW.png")));
            setJumpL(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/jumpLeftSW.png")));
            setAttackR(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/attackRightW.png")));
            setAttackL(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/attackLeftW.png")));
            setTipR(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/tipRightW.png")));
            setTipL(ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/tipLeftW.png")));
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
