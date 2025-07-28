package it.unibo.runwarrior.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.PowerUp;
import it.unibo.runwarrior.model.PowerUpImpl;

/**
 * Class that handles powerup list creation.
 */
public class PowerUpController {
    public static final int END_OF_POWERUP = 222;
    public static final int NUM_POWERUP = 6;
    public static final int FIRST_DISTANCE_POWERUP = 55;
    public static final int OBSTACLE = 5;
    private final List<PowerUp> powerUps;
    private final int tileSize;

    /**
     * Constructor of PowerUp controller, uses the map to set powerup bounds correctly.
     *
     * @param glc game-loop controller
     * @param hM object that prints map tiles
     * @param map current map
     */
    public PowerUpController(final GameLoopController glc, final HandlerMapElement hM, final int[][] map) {
        this.tileSize = hM.getTileSize();
        this.powerUps = new ArrayList<>();
        createList(glc, map);
    }

    private void createList(final GameLoopController glc, final int[][] map) {
        int distance = FIRST_DISTANCE_POWERUP;
        final int space = END_OF_POWERUP / NUM_POWERUP;
        for (int i = 0; i < NUM_POWERUP; i++) {
            final PowerUp p = new PowerUpImpl(glc);
            int row = 0;
            while (map[row][distance] != 2 && map[row][distance] != 1) {
                row++;
                if (row == map.length || map[row][distance] == OBSTACLE) {
                    row = 0;
                    distance += tileSize;
                }
            }
            p.getTouchArea().setBounds(distance * tileSize, (row - 1) * tileSize + (tileSize / 4), 
            tileSize, tileSize - (tileSize / 4));
            powerUps.add(p);
            distance += space;
        }
    }

    /**
     * @return the list of powerups
     */
    public List<PowerUp> getPowerUps() {
        return this.powerUps;
    }
}
