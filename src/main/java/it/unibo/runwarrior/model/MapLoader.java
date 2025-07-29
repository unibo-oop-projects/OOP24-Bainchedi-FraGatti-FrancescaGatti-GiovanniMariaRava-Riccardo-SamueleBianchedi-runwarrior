package it.unibo.runwarrior.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * A utility class to load a map's numerical data from a text file.
 * The class ensures the loaded map conforms to predefined dimensions.
 * This class is final as it is a utility class not designed for extension.
 */
public final class MapLoader {

    /**
     * The expected number of rows for the map.
     */
    public static final int MAP_HEIGHT = 22;

    /**
     * The expected number of columns for the map.
     */
    public static final int MAP_WIDTH = 374;

    // private static final String IN_FILE_STRING = "' in file '";

    private final int[][] mapData;
    private final int rows;
    private final int cols;

    /**
     * Private constructor to create an instance with the loaded map data.
     *
     * @param mapData The 2D integer array representing the map.
     */
    private MapLoader(final int[][] mapData) {
        this.mapData = new int[mapData.length][];
        for (int i = 0; i < mapData.length; i++) {
            this.mapData[i] = mapData[i].clone();
        }
        this.rows = MAP_HEIGHT;
        this.cols = MAP_WIDTH;

    }

    /**
     * Loads map data from a specified resource file.
     * This static factory method reads a text file line by line, parsing characters
     * into integer values to build the map grid.
     *
     * @param mapFilePath The path to the map data file within the resources.
     * @return a new {@link MapLoader} instance, or null if loading fails.
     */
    public static MapLoader load(final String mapFilePath) {
        final int[][] mapData = new int[MAP_HEIGHT][MAP_WIDTH];

        final InputStream inputStream = MapLoader.class.getClassLoader().getResourceAsStream(mapFilePath);
        if (inputStream == null) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            int currentRow = 0;
            while (line != null) {
                if (currentRow >= MAP_HEIGHT) {
                    break;
                }

                final String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    continue;
                }

                if (trimmedLine.length() != MAP_WIDTH) {
                    return null;
                }

                for (int c = 0; c < MAP_WIDTH; c++) {
                    final char blockChar = trimmedLine.charAt(c);
                    final int blockValue = Character.getNumericValue(blockChar);
                    if (blockValue == -1) {
                        return null;
                    }
                    mapData[currentRow][c] = blockValue;
                }
                currentRow++;
                line = br.readLine();
            }

            if (currentRow < MAP_HEIGHT) {
                return null;
            }
        } catch (final IOException e) {
            return null;
        }

        return new MapLoader(mapData);
    }

    /**
     * Gets the numeric value of a block at a specific coordinate.
     *
     * @param r the row index.
     * @param c the column index.
     * @return the block's numeric value, or -1 if coordinates are out of bounds.
     */
    public int getBlock(final int r, final int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
            return this.mapData[r][c];
        }
        return -1;
    }

    /**
     * Gets the total number of rows in the map.
     *
     * @return the number of rows.
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * Gets the total number of columns in the map.
     *
     * @return the number of columns.
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * Gets a defensive copy of the map data.
     *
     * @return a 2D integer array representing the map grid.
     */
    public int[][] getMapData() {
        final int[][] deepCopy = new int[this.mapData.length][];
        for (int i = 0; i < this.mapData.length; i++) {
            deepCopy[i] = this.mapData[i].clone();
        }
        return deepCopy;
    }
}
