package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import java.util.List;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
* Class that detects the collision between the player and the enmies.
*/
public class KillDetectionImpl implements KillDetection {
    private GameLoopPanel glp;
    private HandlerMapElement hM;
    //private PowersHandler powerUpHandler; // vedi sotto
    //private List<EnemyImpl> enemies; // commentato per ricordare che forse è meglio mantenerlo come variabile
    private Rectangle playerArea;
    private long hitWaitTime;
    private int toll = 5;

    /**
     * Constructor
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
    public void checkCollisionWithEnemeies(Character player) {
        playerArea = player.getArea();
        Rectangle swordArea = player.getSwordArea();
        for (EnemyImpl enemy : glp.getEnemyHandler().getEnemies()) {
            if (futureArea(playerArea).intersects(enemy.getBounds())) {
                System.out.println("----- "+ (playerArea.y + playerArea.height) + "---- "+ enemy.getBounds().y);
                if (isTouchingUp(playerArea, enemy.getBounds())) {
                    player.getMovementHandler().setJumpKill();
                    enemy.die();
                }
                else if ((playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x) &&
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
                    !isBehindTile(swordArea.x + hM.getTileSize()/2, swordArea.y + (swordArea.height / 2)) &&
                    !isBehindTile(swordArea.x + swordArea.width - hM.getTileSize()/2, swordArea.y + (swordArea.height / 2))) {
                if ((swordArea.x + swordArea.width >= enemy.getBounds().x && swordArea.x < enemy.getBounds().x)) {
                    enemy.die();
                }
                else if (swordArea.x <= enemy.getBounds().x + enemy.getBounds().width) {
                    enemy.die();
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle futureArea(Rectangle r1) {
        Rectangle futureArea = new Rectangle(r1);
        futureArea.translate(0, it.unibo.runwarrior.controller.CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTouchingUp(Rectangle playerArea, Rectangle enemyArea){
        return playerArea.y + playerArea.height <= enemyArea.y && 
        ((playerArea.x + toll >= enemyArea.x && playerArea.x + toll <= enemyArea.x + enemyArea.width) ||
        (playerArea.x + playerArea.width - toll >= enemyArea.x && playerArea.x + playerArea.width - toll <= enemyArea.x + enemyArea.width));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBehindTile(int x, int y) {
        float indexXtile = x / hM.getTileSize();
        float indexYtile = y / hM.getTileSize();
        int blockIndex = hM.getMap()[(int) indexYtile][(int) indexXtile];
        if (hM.getBlocks().get(blockIndex).getCollision()) {
            return true;
        }
        return false;
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
    public void setHitWaitTime(long lastHit) {
        hitWaitTime = lastHit;
    }
}
