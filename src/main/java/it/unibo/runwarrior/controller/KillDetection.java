package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.List;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

public class KillDetection {
    private GameLoopPanel glp;
    //private PowersHandler powerUpHandler; // vedi sotto
    //private List<EnemyImpl> enemies; // commentato per ricordare che forse è meglio mantenerlo come variabile
    private Rectangle playerArea;
    private long hitWaitTime;
    private int toll = 5;

    public KillDetection(GameLoopPanel glp) {
        this.glp = glp;
    }

    public void checkCollisionWithEnemeies(Character player) {
        System.out.println("index "+glp.getPowersHandler().getPowers());
        playerArea = player.getArea();
        Rectangle futureArea = new Rectangle(playerArea);
        futureArea.translate(player.getSpeed(), player.getMovementHandler().getSpeedJumpDown());
        for(EnemyImpl enemy : glp.getEnemyHandler().getEnemies()){
            if(futureArea.intersects(enemy.getBounds())){
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
            else if(player.getSwordArea().intersects(enemy.getBounds()) && player.getAnimationHandler().isAttacking()){
                if((player.getSwordArea().x + player.getSwordArea().width >= enemy.getBounds().x && player.getSwordArea().x < enemy.getBounds().x)){
                    enemy.die();
                }
                else if(player.getSwordArea().x <= enemy.getBounds().x + enemy.getBounds().width){
                    enemy.die();
                }
            }
        }
    }

    public boolean touch(Rectangle r1, Rectangle r2) {
        Rectangle expanded = new Rectangle(r2.x - 1, r2.y - 1, r2.width + 1, r2.height + 1);
        return expanded.intersects(r1);
    }
}
