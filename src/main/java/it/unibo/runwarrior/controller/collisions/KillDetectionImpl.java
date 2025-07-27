package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;

import it.unibo.runwarrior.model.enemy.api.Enemy;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.AbstractCharacterImpl;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.controller.HandlerMapElement;

/**
* Class that detects the collision between the player and the enmies.
*/
public class KillDetectionImpl implements KillDetection {
    private final GameLoopController glc;
    private final HandlerMapElement hM;
    private EnemyImpl enemyToDie;
    private long hitWaitTime;
    private static final int TOLL = AbstractCharacterImpl.SPEED;

    /**
     * Constructor of kill detection.
     *
     * @param glc game-loop controller
     * @param hM map handler
     */
    public KillDetectionImpl(final GameLoopController glc, final HandlerMapElement hM) {
        this.glc = glc;
        this.hM = hM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkCollisionWithEnemeies(final Character player) {
        final Rectangle playerArea = player.getArea();
        final Rectangle swordArea = player.getSwordArea();
        //ConcurrentModificationException
        for (final EnemyImpl enemy : glc.getEnemyHandler().getEnemies()) {
            if (futureArea(playerArea).intersects(enemy.getBounds())) {
                //System.out.println("----- "+ (playerArea.y + playerArea.height) + "---- "+ enemy.getBounds().y);
                if (isTouchingUp(playerArea, enemy.getBounds())) {
                    player.getMovementHandler().setJumpKill();
                    enemyToDie = enemy;
                }
                else if (playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x &&
                        System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glc.getPowersHandler().losePower(true);
                }
                else if (playerArea.x <= enemy.getBounds().x + enemy.getBounds().width && System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glc.getPowersHandler().losePower(true);
                }
            }
            else if (swordArea.intersects(enemy.getBounds()) && player.getAnimationHandler().isAttacking() &&
                    !isBehindTile(swordArea.x + hM.getTileSize() / 2, swordArea.y + (swordArea.height / 2)) &&
                    !isBehindTile(swordArea.x + swordArea.width - hM.getTileSize() / 2, swordArea.y + (swordArea.height / 2)) ) {
                enemyToDie = enemy;
            }
        }
        glc.getEnemyHandler().removeEnemy(enemyToDie);
    }

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    private Rectangle futureArea(final Rectangle r1) {
        final Rectangle futureArea = new Rectangle(r1);
        futureArea.translate(0, it.unibo.runwarrior.controller.CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    /**
     * Control if the collision is from above the enemy.
     *
     * @param playerArea player collision area
     * @param enemyArea enemy collision area
     * @return true if the player touches the enemy in his head
     */
    private boolean isTouchingUp(final Rectangle playerArea, final Rectangle enemyArea){
        return playerArea.y + playerArea.height <= enemyArea.y && 
        ((playerArea.x + TOLL >= enemyArea.x && playerArea.x + TOLL <= enemyArea.x + enemyArea.width) ||
        (playerArea.x + playerArea.width - TOLL >= enemyArea.x && playerArea.x + playerArea.width - TOLL <= enemyArea.x + enemyArea.width));
    }

    /**
     * Controls if the given point (x, y) is touching a solid tile.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the point touches a solid tile
     */
    private boolean isBehindTile(final int x, final int y) {
        final float indexXtile = x / hM.getTileSize();
        final float indexYtile = y / hM.getTileSize();
        final int blockIndex = hM.getMap()[(int) indexYtile][(int) indexXtile];
        return hM.getBlocks().get(blockIndex).getCollision();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getHitWaitTime() {
        return this.hitWaitTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHitWaitTime(final long lastHit) {
        hitWaitTime = lastHit;
    }
}
