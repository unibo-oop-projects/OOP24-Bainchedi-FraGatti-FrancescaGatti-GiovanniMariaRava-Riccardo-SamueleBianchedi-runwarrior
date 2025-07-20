package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.List;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
* Class that detects the collision between the player and the enmies.
*/
public class KillDetection {
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
    public KillDetection(final GameLoopPanel glp, final HandlerMapElement hM) {
        this.glp = glp;
        this.hM = hM;
    }

    /**
     * Checks the collision with every enemies present in the map.
     * If the player jump on their head, they die. If the touch the player from left or right, the player loses a life.
     * If the player has the sword, he can kill them with it.
     *
     * @param player current player
     */
    public void checkCollisionWithEnemeies(Character player) {
        System.out.println("index "+glp.getPowersHandler().getPowers());
        playerArea = player.getArea();
        for (EnemyImpl enemy : glp.getEnemyHandler().getEnemies()) {
            if (futureArea(playerArea, player).intersects(enemy.getBounds())) {
                System.out.println("----- "+ (playerArea.y + playerArea.height) + "---- "+ enemy.getBounds().y);
                if (playerArea.y + playerArea.height <= enemy.getBounds().y &&
                    ((playerArea.x + toll >= enemy.getBounds().x && playerArea.x + toll <= enemy.getBounds().x + enemy.getBounds().width) ||
                     (playerArea.x + playerArea.width - toll >= enemy.getBounds().x && playerArea.x + playerArea.width - toll <= enemy.getBounds().x + enemy.getBounds().width))) {
                    player.getMovementHandler().setJumpKill();
                    enemy.die();
                }
                else if ((playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x) &&
                        System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower();
                }
                else if (playerArea.x <= enemy.getBounds().x + enemy.getBounds().width && System.currentTimeMillis() - hitWaitTime > 3000) {
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower();
                }
            }
            else if (player.getSwordArea().intersects(enemy.getBounds()) && player.getAnimationHandler().isAttacking() &&
                    !isBehindTile(player.getSwordArea().x + hM.getTileSize()/2, player.getSwordArea().y) &&
                    !isBehindTile(player.getSwordArea().x + player.getSwordArea().width - hM.getTileSize()/2, player.getSwordArea().y)) {
                if ((player.getSwordArea().x + player.getSwordArea().width >= enemy.getBounds().x && player.getSwordArea().x < enemy.getBounds().x)) {
                    enemy.die();
                }
                else if (player.getSwordArea().x <= enemy.getBounds().x + enemy.getBounds().width) {
                    enemy.die();
                }
            }
        }
    }

    /**
     * Creates the future area of the falling player
     *
     * @param r1 collision area
     * @param pl player
     * @return the collision area the player will have
     */
    public Rectangle futureArea(Rectangle r1, Character pl) {
        Rectangle futureArea = new Rectangle(playerArea);
        futureArea.translate(0, CharacterMovementHandlerImpl.SPEED_JUMP_DOWN);
        return futureArea;
    }

    /**
     * Controls if the given point (x, y) is touching a solid tile.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the point touches a solid tile
     */
    public boolean isBehindTile(int x, int y) {
        float indexXtile = x / hM.getTileSize();
        float indexYtile = y / hM.getTileSize();
        System.out.println(indexXtile + " " + indexYtile);
        int blockIndex = hM.getMap()[(int) indexYtile][(int) indexXtile];
        if (hM.getBlocks().get(blockIndex).getCollision()) {
            System.out.println("si");
            return true;
        }
        System.out.println("no");
        return false;
    }

    /**
     * @return the moment when the player was hit.
     */
    public long getHitWaitTime() {
        return this.hitWaitTime;
    }

    /**
     * Set the last time the player was hit.
     *
     * @param lastHit time of the last hit
     */
    public void setHitWaitTime(long lastHit) {
        hitWaitTime = lastHit;
    }
}
