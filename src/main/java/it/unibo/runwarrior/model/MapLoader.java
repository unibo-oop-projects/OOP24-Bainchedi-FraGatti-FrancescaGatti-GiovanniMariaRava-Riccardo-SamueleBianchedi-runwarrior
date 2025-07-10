package it.unibo.runwarrior.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoader {


    public static final int ALTEZZA_MAPPA = 22;
    public static final int LARGHEZZA_MAPPA = 374;

    private int[][] mapData;
    private int rows;
    private int cols;

    private MapLoader(int[][] mapData) {
        this.mapData = mapData;
        this.rows = ALTEZZA_MAPPA;
        this.cols = LARGHEZZA_MAPPA;
    }

    public int getBlock(int r, int c) {
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            return mapData[r][c];
        }
        return -1;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public static MapLoader load(String mapFilePath) {
        final int expectedRows = ALTEZZA_MAPPA;
        final int expectedCols = LARGHEZZA_MAPPA;

        int[][] mapData = new int[expectedRows][expectedCols];
        int currentRow = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(mapFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (currentRow >= expectedRows) {
                    System.err.println("Avviso: File mappa '" + mapFilePath + "' contiene più righe del previsto (" + expectedRows + "). Righe extra ignorate.");
                    break;
                }

                String trimmedLine = line.trim();
                
                if (trimmedLine.isEmpty()) {
                    System.err.println("Avviso: Riga vuota o solo spazi a riga " + currentRow + " nel file '" + mapFilePath + "'. Questa riga sarà ignorata.");
                    continue;
                }
                
                if (trimmedLine.length() != expectedCols) {
                    System.err.println("Errore: Riga " + currentRow + " nel file '" + mapFilePath +
                                       "' ha un numero di caratteri incoerente. Previsto: " + expectedCols +
                                       ", Trovato: " + trimmedLine.length() + ".");
                    return null;
                }

                for (int c = 0; c < expectedCols; c++) {
                    char blockChar = trimmedLine.charAt(c);
                    int blockValue = java.lang.Character.getNumericValue(blockChar);

                    if (blockValue == -1) {
                         System.err.println("Errore: Valore non numerico ('" + blockChar + "') a riga " + currentRow +
                                           ", colonna " + c + " nel file '" + mapFilePath + "'.");
                        return null;
                    }
                    mapData[currentRow][c] = blockValue;
                }
                
                currentRow++;
            }

            if (currentRow < expectedRows) {
                System.err.println("Errore: File mappa '" + mapFilePath + "' contiene meno righe del previsto. Previsto: " + expectedRows + ", Trovato: " + currentRow + ".");
                return null;
            }

        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del file mappa '" + mapFilePath + "': " + e.getMessage());
            return null;
        }

        System.out.println("Mappa caricata da '" + mapFilePath + "': " + expectedRows + "x" + expectedCols + " blocchi (dimensioni predefinite).");
        return new MapLoader(mapData);
    }
}