package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

public class EnemyHandler {
    public LinkedList<EnemyImpl> enemies;

    GameLoopPanel glp;
    public EnemyHandler (GameLoopPanel glp){
        this.glp = glp;
        this.enemies = new LinkedList<>();
    }


    public void render(Graphics g){
        for (EnemyImpl enemy : enemies) {
            enemy.render(g);
        }
    }

    public void update(){
        Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            EnemyImpl enemy = iterator.next();
            enemy.update();
        }
    }
    
    public void addEnemy(EnemyImpl en){
        enemies.add(en);
    }

    public void removeEnemy(EnemyImpl en){
        enemies.remove(en);
    }
    
    public void updateWithMap(List<Rectangle> mapObstacles){

        System.out.println("Updating " + enemies.size() + " enemies");
        Iterator<EnemyImpl> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            EnemyImpl enemy = iterator.next();
            
            
            
            if (enemy.getX() + enemy.getWidth() < glp.getPlayer().getArea().x - GameLoopPanel.WIDTH) {
                iterator.remove(); 
                continue;
            }
            enemy.update();
            if (isEnemyInView(enemy)) {
                
                enemy.checkMapCollision(mapObstacles);
            }
        }
    }
    
    private boolean isEnemyInView(EnemyImpl enemy) {
        int cameraX = glp.getPlayer().getArea().x;
        int enemyX = enemy.getX();
        int enemyWidth = enemy.getWidth();
        
        return enemyX + enemyWidth >= cameraX  && enemyX <= cameraX + GameLoopPanel.WIDTH;
    }
    
    public LinkedList<EnemyImpl> getEnemies() {
        return enemies;
    }
}
