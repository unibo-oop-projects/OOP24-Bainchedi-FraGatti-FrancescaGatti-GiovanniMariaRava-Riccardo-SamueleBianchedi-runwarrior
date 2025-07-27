package it.unibo.runwarrior.model.player;

import java.io.IOException;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowerUpController;

/**
 * Implementation of the wizard without powerups.
 */
public class NakedWizard extends AbstractCharacterImpl {

    /**
     * Constructor of this skin.
     *
     * @param glc game-loop controller
     * @param commands object that handles keyboard input
     * @param mapHandler object that prints tiles
     * @param pCon object that creates powerup list
     */
    public NakedWizard(final GameLoopController glc, final CharacterComand commands,
        final HandlerMapElement mapHandler, final PowerUpController pCon) {
        super(glc, commands, mapHandler, pCon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/stopRightNW.png"));
            right1 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/goRightNW1.png"));
            right2 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/goRightNW2.png"));
            left0 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/stopLeftNW.png"));
            left1 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/goLeftNW1.png"));
            left2 = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/goLeftNW2.png"));
            jumpR = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/jumpRightNW.png"));
            jumpL = ImageIO.read(NakedWizard.class.getResourceAsStream("/WizardImages/jumpLeftNW.png"));
            attackR = right0;
            attackL = left0;
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
