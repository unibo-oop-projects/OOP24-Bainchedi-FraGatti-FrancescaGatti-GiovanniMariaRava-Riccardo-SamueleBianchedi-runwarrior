package it.unibo.runwarrior.view;

import java.awt.Graphics;
import java.util.*;

import it.unibo.runwarrior.model.EnemyImpl;

public class Handler {
    public LinkedList<EnemyImpl> enemies = new LinkedList<>();

    public void render(Graphics g){
        for (EnemyImpl enemy : enemies) enemy.render(g);
    }

    public void update(){
        for (EnemyImpl enemy : enemies) enemy.update();
    }

    

}
