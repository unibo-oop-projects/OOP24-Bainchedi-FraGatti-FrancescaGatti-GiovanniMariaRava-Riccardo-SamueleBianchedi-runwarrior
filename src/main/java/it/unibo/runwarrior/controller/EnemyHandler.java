package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;

public class EnemyHandler {
    private LinkedList<EnemyImpl> enemies;
    private EnemyViewFactoryImpl viewFactory;

    GameLoopPanel glp;
    public EnemyHandler (GameLoopPanel glp, EnemyViewFactoryImpl viewFactory){
        this.viewFactory = viewFactory;
        this.glp = glp;
        this.enemies = new LinkedList<>();
    }


    public void render(Graphics g){
        for (EnemyImpl enemy : enemies) {
            viewFactory.get(enemy.getType()).render(g, enemy);
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
