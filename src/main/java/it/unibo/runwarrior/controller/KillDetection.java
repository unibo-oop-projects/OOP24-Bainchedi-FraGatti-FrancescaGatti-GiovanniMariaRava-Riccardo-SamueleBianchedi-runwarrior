package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.List;

import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

public class KillDetection {
    private GameLoopPanel glp;
    private HandlerMapElement hM;
    //private PowersHandler powerUpHandler; // vedi sotto
    //private List<EnemyImpl> enemies; // commentato per ricordare che forse è meglio mantenerlo come variabile
    private Rectangle playerArea;
    private long hitWaitTime;
    private int toll = 5;

    public KillDetection(GameLoopPanel glp, HandlerMapElement hM) {
        this.glp = glp;
        this.hM = hM;
    }

    public void checkCollisionWithEnemeies(Character player) {
        System.out.println("index "+glp.getPowersHandler().getPowers());
        playerArea = player.getArea();
        for(EnemyImpl enemy : glp.getEnemyHandler().getEnemies()){
            if(futureArea(playerArea, player).intersects(enemy.getBounds())){
                System.out.println("----- "+ (playerArea.y + playerArea.height) + "---- "+ enemy.getBounds().y);
                if(playerArea.y + playerArea.height <= enemy.getBounds().y &&
                    ((playerArea.x + toll >= enemy.getBounds().x && playerArea.x + toll <= enemy.getBounds().x + enemy.getBounds().width) ||
                     (playerArea.x + playerArea.width - toll >= enemy.getBounds().x && playerArea.x + playerArea.width - toll <= enemy.getBounds().x + enemy.getBounds().width))){
                    player.getMovementHandler().setJumpKill();
                    enemy.die();
                }
                else if((playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x) &&
                        System.currentTimeMillis() - hitWaitTime > 3000){
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower();
                }
                else if(playerArea.x <= enemy.getBounds().x + enemy.getBounds().width && System.currentTimeMillis() - hitWaitTime > 3000){
                    hitWaitTime = System.currentTimeMillis();
                    glp.getPowersHandler().losePower();
                }
            }
            else if(player.getSwordArea().intersects(enemy.getBounds()) && player.getAnimationHandler().isAttacking() &&
                    !isBehindTile(player.getSwordArea().x + hM.getTileSize()/2, player.getSwordArea().y) &&
                    !isBehindTile(player.getSwordArea().x + player.getSwordArea().width - hM.getTileSize()/2, player.getSwordArea().y)){
                if((player.getSwordArea().x + player.getSwordArea().width >= enemy.getBounds().x && player.getSwordArea().x < enemy.getBounds().x)){
                    enemy.die();
                }
                else if(player.getSwordArea().x <= enemy.getBounds().x + enemy.getBounds().width){
                    enemy.die();
                }
            }
        }
    }

    public Rectangle futureArea(Rectangle r1, Character pl) {
        Rectangle futureArea = new Rectangle(playerArea);
        futureArea.translate(0, pl.getMovementHandler().getSpeedJumpDown());
        return futureArea;
    }

    /**
     * @param sword
     * @return if the sword is touching a solid tile
     * Controls that with 2 points inside of the sword area
     */
    public boolean isBehindTile(int x, int y){
        float indexXtile = x / hM.getTileSize();
        float indexYtile = y / hM.getTileSize();
        System.out.println(indexXtile + " " + indexYtile);
        int blockIndex = hM.getMap()[(int) indexYtile][(int) indexXtile];
        if(hM.getBlocks().get(blockIndex).getCollision()){
            System.out.println("si");
            return true;
        }
        System.out.println("no");
        return false;
    }

    public long getHitWaitTime() {
        return this.hitWaitTime;
    }

    public void setHitWaitTime(long lastHit) {
        hitWaitTime = lastHit;
    }
}
