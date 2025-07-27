package it.unibo.runwarrior.view.enemy.api;

/**
 * The EnemyViewFactory defines the method that are used to map the enemy.
 */

public interface EnemyViewFactory {
    /**
     * @param type indicates the different enemies
     * @param view refers to the interface to render the enemy
     */
    void register(int type, EnemyView view);

    /**
     * @param type indicates the different enemies
     * @return the enemy of the type requested
     */
    EnemyView get(int type);
}
