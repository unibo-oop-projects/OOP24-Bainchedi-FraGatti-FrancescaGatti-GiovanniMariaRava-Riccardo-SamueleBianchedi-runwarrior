package it.unibo.runwarrior.view.enemy.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.runwarrior.view.enemy.api.EnemyView;
import it.unibo.runwarrior.view.enemy.api.EnemyViewFactory;

public class EnemyViewFactoryImpl implements EnemyViewFactory{
    private final Map<Integer, EnemyView> viewsByType = new HashMap<>();

    public void register(int type, EnemyView view) {
        viewsByType.put(type, view);
    }
    public EnemyView get(int type) {
        return viewsByType.get(type);
    }
}
