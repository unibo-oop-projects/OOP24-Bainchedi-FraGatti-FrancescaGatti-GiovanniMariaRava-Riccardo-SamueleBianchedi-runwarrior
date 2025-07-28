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
            setRight0(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/stopRightAW.png")));
            setRight1(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goRightAW1.png")));
            setRight2(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goRightAW2.png")));
            setLeft0(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/stopLeftAW.png")));
            setLeft1(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goLeftAW1.png")));
            setLeft2(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/goLeftAW2.png")));
            setJumpR(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/jumpRightAW.png")));
            setJumpL(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/jumpLeftAW.png")));
            setAttackR(ImageIO.read(ArmourWizard.class.getResourceAsStream("/WizardImages/stopRightAW.png")));
            setAttackL(ImageIO.read(ArmourWarrior.class.getResourceAsStream("/WizardImages/stopLeftAW.png")));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot load player images");
        }
    }
}
