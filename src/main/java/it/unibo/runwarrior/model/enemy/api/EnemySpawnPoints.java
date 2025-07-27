package it.unibo.runwarrior.model.enemy.api;

/**
 * Record of the enemies' position.
 *
 * @param type is the different type of the enemy
 * @param x is the starting x
 * @param y is the starting y
 */
public record EnemySpawnPoints(int type, int x, int y) {

}
