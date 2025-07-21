package it.unibo.runwarrior.view.enemy.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.runwarrior.view.enemy.api.EnemyView;
import it.unibo.runwarrior.view.enemy.api.EnemyViewFactory;
/**
 * Implements the ENemyViewFactory method and map the enemies
 */
public class EnemyViewFactoryImpl implements EnemyViewFactory {
    private final Map<Integer, EnemyView> viewsByType = new HashMap<>();
    /**
     *{@inheritDoc} 
     */
    public void register(final int type, final EnemyView view) {
        viewsByType.put(type, view);
    }
    /**
     *{@inheritDoc} 
     */
    public EnemyView get(final int type) {
        return viewsByType.get(type);
    }
}
