package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.*;

import it.unibo.runwarrior.model.EnemyImpl;

public class EnemyHandler {
    public LinkedList<EnemyImpl> enemies = new LinkedList<>();

    public void render(Graphics g){
        for (EnemyImpl enemy : enemies) {
            enemy.render(g);
            System.out.println("stampo nemico");
        }
    }

    public void update(){
        for (EnemyImpl enemy : enemies) enemy.update();
    }
    
    public void addEnemy(EnemyImpl en){
        enemies.add(en);
    }

    public void removeEnemy(EnemyImpl en){
        enemies.remove(en);
    }
    
    public void updateWithMap(List<Rectangle> mapObstacles){
        System.out.println("Updating " + enemies.size() + " enemies");
        for (EnemyImpl enemy : enemies) {
            enemy.update();
            enemy.checkMapCollision(mapObstacles);
            System.out.println("Enemy at: " + enemy.x + "," + enemy.y);
        }
    }
    
}
