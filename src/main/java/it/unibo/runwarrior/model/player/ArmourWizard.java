package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;
/**
 * Implementation of the wizard with cape.
 */
public class ArmourWizard extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public ArmourWizard(final GameLoopController glc, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/stopRightAW.png"));
            right1 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goRightAW1.png"));
            right2 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goRightAW2.png"));
            left0 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/stopLeftAW.png"));
            left1 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goLeftAW1.png"));
            left2 = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goLeftAW2.png"));
            jumpR = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/jumpRightAW.png"));
            jumpL = ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/jumpLeftAW.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
