package it.unibo.runwarrior.controller;

import java.awt.Graphics;
import java.util.*;

import it.unibo.runwarrior.model.EnemyImpl;

public class EnemyHandler {
    public LinkedList<EnemyImpl> enemies = new LinkedList<>();

    public void render(Graphics g){
        for (EnemyImpl enemy : enemies) enemy.render(g);
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
    
}
