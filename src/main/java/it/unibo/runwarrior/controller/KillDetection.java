package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.List;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.EnemyImpl;

public class KillDetection {
    private PowersHandler powerUpHandler;
    private List<EnemyImpl> enemies;
    private Rectangle playerArea;
    private int playerSpeed;
    private int toll = 5;

    public KillDetection(EnemyHandler eHandler, PowersHandler powerHandler) {
        enemies = eHandler.getEnemies();
        powerUpHandler = powerHandler;
    }

    public void checkCollisionWithEnemeies(Character player) {
        playerArea = player.getArea();
        for(EnemyImpl enemy : enemies){
            if(playerArea.intersects(enemy.getBounds())){
                if(playerArea.y + playerArea.height == enemy.getBounds().y &&
                    ((playerArea.x + toll >= enemy.getBounds().x && playerArea.x + toll <= enemy.getBounds().x + enemy.getBounds().width) ||
                     (playerArea.x + playerArea.width - toll >= enemy.getBounds().x && playerArea.x + playerArea.width - toll <= enemy.getBounds().x + enemy.getBounds().width))){
                    player.getMovementHandler().setJumpKill();
                    enemy.die();
                }
                else if((playerArea.x + playerArea.width >= enemy.getBounds().x && playerArea.x < enemy.getBounds().x)){
                    powerUpHandler.losePower();
                }
                else if(playerArea.x <= enemy.getBounds().x + enemy.getBounds().width){
                    powerUpHandler.losePower();
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
}
