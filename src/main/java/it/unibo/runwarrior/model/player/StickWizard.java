package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;

/**
 * Implementation of the wizard with cape and stick.
 */
public class StickWizard extends CharacterImpl {
    /**
     * Constructor of this skin.
     *
     * @param panel game-loop panel
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pMan object that prints powerups
     */
    public StickWizard(final GameLoopPanel panel, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpManager pMan) {
        super(panel, commands, mapHandler, pMan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/stopRightSW.png"));
            right1 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goRightSW1.png"));
            right2 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goRightSW2.png"));
            left0 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/stopLeftSW.png"));
            left1 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goLeftSW1.png"));
            left2 = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/goLeftSW2.png"));
            jumpR = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/jumpRightSW.png"));
            jumpL = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/jumpLeftSW.png"));
            attackR = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/attackRightW.png"));
            attackL = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/attackLeftW.png"));
            tipR = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/tipRightW.png"));
            tipL = ImageIO.read(StickWizard.class.getResourceAsStream("/WizardImages/tipLeftW.png"));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAttackCollision() {
        if (animation.getFrame() == PlayerFrame.ATTACK_FRAME && rightDirection) {
            swordArea.setBounds(movement.getPlX() + sizeCharacter, movement.getPlY() + sizeCharacter / 4,
            sizeCharacter, sizeCharacter - (sizeCharacter / 4) - (TO_TOUCH_FLOOR * 2));
        }
        if (animation.getFrame() == PlayerFrame.ATTACK_FRAME && !rightDirection) {
            swordArea.setBounds(movement.getPlX() - sizeCharacter, movement.getPlY() + sizeCharacter / 4,
            sizeCharacter, sizeCharacter - (sizeCharacter / 4) - (TO_TOUCH_FLOOR * 2));
        }
        if (!cmd.getAttack()) {
            collisionArea.setSize(sizeCharacter / 2, sizeCharacter - (sizeCharacter / 4) - TO_TOUCH_FLOOR);
        }
    }
}
