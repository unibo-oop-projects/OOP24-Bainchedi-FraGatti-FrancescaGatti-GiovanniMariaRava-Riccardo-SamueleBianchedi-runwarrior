package it.unibo.runwarrior.model.player;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

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
     * @param pFact object that prints powerups
     */
    public StickWizard(final GameLoopPanel panel, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpFactoryImpl pFact) {
        super(panel, commands, mapHandler, pFact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/stopRightSW.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goRightSW1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goRightSW2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/stopLeftSW.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goLeftSW1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goLeftSW2.png"));
            jumpR = ImageIO.read(getClass().getResourceAsStream("/WizardImages/jumpRightSW.png"));
            jumpL = ImageIO.read(getClass().getResourceAsStream("/WizardImages/jumpLeftSW.png"));
            attackR = ImageIO.read(getClass().getResourceAsStream("/WizardImages/attackRightW.png"));
            attackL = ImageIO.read(getClass().getResourceAsStream("/WizardImages/attackLeftW.png"));
            tipR = ImageIO.read(getClass().getResourceAsStream("/WizardImages/tipRightW.png"));
            tipL = ImageIO.read(getClass().getResourceAsStream("/WizardImages/tipLeftW.png"));
        } catch (final IOException e) {
            e.printStackTrace();
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
