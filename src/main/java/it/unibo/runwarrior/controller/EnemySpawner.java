package it.unibo.runwarrior.controller;

import java.util.List;
import java.util.logging.Handler;

import it.unibo.runwarrior.model.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

public class EnemySpawner {
    private Handler handler;
    private GameLoopPanel glp;
    private List<EnemyImpl> enemies;
    public EnemySpawner(Handler handler, GameLoopPanel glp) {
        this.handler = handler;
        this.glp = glp;
    }
    
}
