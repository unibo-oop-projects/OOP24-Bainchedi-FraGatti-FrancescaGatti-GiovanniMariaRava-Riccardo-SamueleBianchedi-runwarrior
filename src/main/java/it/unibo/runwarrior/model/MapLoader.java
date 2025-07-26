package it.unibo.runwarrior.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

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

    private static final String IN_FILE_STRING = "' in file '";

    private final int[][] mapData;
    private final int rows;
    private final int cols;

    /**
     * Private constructor to create an instance with the loaded map data.
     * @param mapData The 2D integer array representing the map.
     */
    private MapLoader(final int[][] mapData) {
        this.mapData = mapData.clone();
        this.rows = MAP_HEIGHT;
        this.cols = MAP_WIDTH;
    }

    /**
     * Gets the numeric value of a block at a specific coordinate.
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
        return this.mapData.clone();
    }

    /**
     * Loads map data from a specified resource file.
     * This static factory method reads a text file line by line, parsing characters
     * into integer values to build the map grid.
     * @param mapFilePath The path to the map data file within the resources.
     * 
     * @return a new {@link MapLoader} instance, or null if loading fails.
     */
    public static MapLoader load(final String mapFilePath) {
        final int[][] mapData = new int[MAP_HEIGHT][MAP_WIDTH];
        int currentRow = 0;

        try (InputStream inputStream = MapLoader.class.getClassLoader().getResourceAsStream(mapFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (currentRow >= MAP_HEIGHT) {
                    System.err.println("Warning: Map file '" + mapFilePath + "' has more rows than expected. Extra rows ignored.");
                    break;
                }

                final String trimmedLine = line.trim();
                if (trimmedLine.isEmpty()) {
                    System.err.println("Warning: Empty row at line " + currentRow + IN_FILE_STRING + mapFilePath + "'. Ignoring.");
                    continue;
                }

                if (trimmedLine.length() != MAP_WIDTH) {
                    System.err.println("Error: Row " + currentRow + IN_FILE_STRING + mapFilePath
                        + "' has inconsistent length. Expected: " + MAP_WIDTH
                        + ", Found: " + trimmedLine.length() + ".");
                    return null;
                }

                for (int c = 0; c < MAP_WIDTH; c++) {
                    final char blockChar = trimmedLine.charAt(c);
                    final int blockValue = Character.getNumericValue(blockChar);
                    if (blockValue == -1) {
                        System.err.println("Error: Non-numeric value ('" + blockChar + "') at row " + currentRow
                            + ", col " + c + IN_FILE_STRING + mapFilePath + "'.");
                        return null;
                    }
                    mapData[currentRow][c] = blockValue;
                }
                currentRow++;
            }

            if (currentRow < MAP_HEIGHT) {
                System.err.println("Error: Map file '" + mapFilePath + "' has fewer rows than expected. Expected: "
                    + MAP_HEIGHT + ", Found: " + currentRow + ".");
                return null;
            }
        } catch (final IOException | NullPointerException e) {
            System.err.println("Error loading map file '" + mapFilePath + "': " + e.getMessage());
            return null;
        }

        System.out.println("Map loaded from '" + mapFilePath + "': " + MAP_HEIGHT + "x" + MAP_WIDTH + " blocks.");
        return new MapLoader(mapData);
    }
}
