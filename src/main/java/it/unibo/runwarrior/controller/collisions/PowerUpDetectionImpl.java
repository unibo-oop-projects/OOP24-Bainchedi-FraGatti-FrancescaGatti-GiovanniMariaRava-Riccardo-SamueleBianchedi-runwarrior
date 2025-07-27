package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;
import it.unibo.runwarrior.controller.CharacterMovementHandler;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.PowerUpController;
import it.unibo.runwarrior.model.PowerUp;

/**
 * Class that handles the collision between the player and the powerups.
 */
public class PowerUpDetectionImpl implements PowerUpDetection {
    private static final int TOLL = AbstractCharacterImpl.SPEED;
    private static final int WAIT = 200;
    private final GameLoopController glc;
    private final PowerUpController powersController;
    private List<PowerUp> powerCollision;
    private long hitWaitTime;

    /**
     * Constructor of powerup detection.
     *
     * @param glc game-loop controller
     * @param pCon object that creates powerup list
     */
    public PowerUpDetectionImpl(final GameLoopController glc, final PowerUpController pCon) {
        this.glc = glc;
        this.powersController = pCon;
        this.powerCollision = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkCollisionWithPowers(final Character player, final CharacterMovementHandler move) {
        powerCollision.addAll(powersController.getPowerUps());
        final Rectangle playerArea = player.getArea();
        String dir = "";
        for (final PowerUp pUp : powerCollision) {
            if (futureArea(playerArea).intersects(pUp.getTouchArea()) && !pUp.getTouchArea().isEmpty()) {
                if (isTouchingUp(playerArea, pUp.getTouchArea())) {
                    dir = "up";
                    if (pUp.isEggOpen() && !pUp.isPowerTaken() && System.currentTimeMillis() - hitWaitTime > WAIT) {
                        glc.getPowersHandler().setPowers();
                        pUp.takePower();
                    } else if (!pUp.isEggOpen()) {
                        move.setJumpKill();
                        pUp.openTheEgg();
                        hitWaitTime = System.currentTimeMillis();
                    }
                } else if (playerArea.x + playerArea.width >= pUp.getTouchArea().x && playerArea.x < pUp.getTouchArea().x) {
                    dir = "right";
                    if (pUp.isEggOpen() && !pUp.isPowerTaken()) {
                        glc.getPowersHandler().setPowers();
                        pUp.takePower();
                    }
                } else if (playerArea.x <= pUp.getTouchArea().x + pUp.getTouchArea().width) {
                    dir = "left";
                    if (pUp.isEggOpen() && !pUp.isPowerTaken()) {
                        glc.getPowersHandler().setPowers();
                        pUp.takePower();
                    }
                }
            }
        }
        return dir;
    }

    /**
     * Creates the future area of the falling player.
     *
     * @param r1 collision area
     * @return the collision area the player will have
     */
    private Rectangle futureArea(final Rectangle r1) {
        final Rectangle futureArea = new Rectangle(r1);
        futureArea.translate(0, it.unibo.runwarrior.controller.CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    /**
     * Control if the collision is from above the powerup.
     *
     * @param playerArea player collision area
     * @param pUpArea powerup collision area
     * @return true if the player touches the powerup with his feet
     */
    private boolean isTouchingUp(final Rectangle playerArea, final Rectangle pUpArea) {
        return playerArea.y + playerArea.height <= pUpArea.y 
        && (playerArea.x + TOLL >= pUpArea.x && playerArea.x + TOLL <= pUpArea.x + pUpArea.width
        || playerArea.x + playerArea.width - TOLL >= pUpArea.x 
        && playerArea.x + playerArea.width - TOLL <= pUpArea.x + pUpArea.width);
    }
}
