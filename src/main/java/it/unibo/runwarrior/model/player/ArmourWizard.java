package it.unibo.runwarrior.model.player;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

/**
 * Implementation of the wizard with cape.
 */
public class ArmourWizard extends CharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param panel game-loop panel
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pFact object that prints powerups
     */
    public ArmourWizard(final GameLoopPanel panel, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpFactoryImpl pFact) {
        super(panel, commands, mapHandler, pFact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/stopRightAW.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goRightAW1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goRightAW2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/stopLeftAW.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goLeftAW1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WizardImages/goLeftAW2.png"));
            jumpR = ImageIO.read(getClass().getResourceAsStream("/WizardImages/jumpRightAW.png"));
            jumpL = ImageIO.read(getClass().getResourceAsStream("/WizardImages/jumpLeftAW.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAttackCollision() {
        // cannot attack
    }
    
}
