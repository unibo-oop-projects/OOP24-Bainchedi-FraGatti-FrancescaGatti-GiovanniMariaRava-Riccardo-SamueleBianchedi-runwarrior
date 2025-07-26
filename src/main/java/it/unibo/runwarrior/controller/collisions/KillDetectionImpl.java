package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import java.util.List;

import it.unibo.runwarrior.model.enemy.api.Enemy;
import it.unibo.runwarrior.model.enemy.impl.EnemyImpl;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.player.CharacterImpl;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
* Class that detects the collision between the player and the enmies.
*/
public class KillDetectionImpl implements KillDetection {
    private final GameLoopPanel glp;
    private final HandlerMapElement hM;
    //private PowersHandler powerUpHandler; // vedi sotto
    private EnemyImpl enemyToDie; // commentato per ricordare che forse è meglio mantenerlo come variabile
    private long hitWaitTime;
    private static final int TOLL = CharacterImpl.SPEED;

    /**
     * Constructor of kill detection.
     *
     * @param glp game-loop panel
     * @param hM map handler
     */
    public KillDetectionImpl(final GameLoopPanel glp, final HandlerMapElement hM) {
        this.glp = glp;
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
        for (final EnemyImpl enemy : glp.getEnemyHandler().getEnemies()) {
            if (futureArea(playerArea).intersects(enemy.getBounds())) {
                //System.out.println("----- "+ (playerArea.y + playerArea.height) + "---- "+ enemy.getBounds().y);
                if (isTouchingUp(playerArea, enemy.getBounds())) {
                    player.getMovementHandler().setJumpKill();
                    enemyToDie = enemy;
                }
                else if (playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x &&
                        System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower(true);
                }
                else if (playerArea.x <= enemy.getBounds().x + enemy.getBounds().width && System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower(true);
                }
            }
            else if (swordArea.intersects(enemy.getBounds()) && player.getAnimationHandler().isAttacking() &&
                    !isBehindTile(swordArea.x + hM.getTileSize() / 2, swordArea.y + (swordArea.height / 2)) &&
                    !isBehindTile(swordArea.x + swordArea.width - hM.getTileSize() / 2, swordArea.y + (swordArea.height / 2)) ) {
                enemyToDie = enemy;
            }
        }
        glp.getEnemyHandler().removeEnemy(enemyToDie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle futureArea(final Rectangle r1) {
        final Rectangle futureArea = new Rectangle(r1);
        futureArea.translate(0, it.unibo.runwarrior.controller.CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTouchingUp(final Rectangle playerArea, final Rectangle enemyArea){
        return playerArea.y + playerArea.height <= enemyArea.y && 
        ((playerArea.x + TOLL >= enemyArea.x && playerArea.x + TOLL <= enemyArea.x + enemyArea.width) ||
        (playerArea.x + playerArea.width - TOLL >= enemyArea.x && playerArea.x + playerArea.width - TOLL <= enemyArea.x + enemyArea.width));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBehindTile(final int x, final int y) {
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
