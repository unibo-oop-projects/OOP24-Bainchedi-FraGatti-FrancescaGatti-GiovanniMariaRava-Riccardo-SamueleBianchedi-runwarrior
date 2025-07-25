package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.util.Map;

//import model.MapLoader;
//import model.ImageLoader;

public class GameMap {

    private int[][] mapData;
    private int rows;
    private int cols;
    private Map<Integer, BufferedImage> blockImages;

    public GameMap(int[][] mapData, Map<Integer, BufferedImage> blockImages, int rows, int cols) {
        this.mapData = mapData;
        this.blockImages = blockImages;
        this.rows = rows;
        this.cols = cols;
    }

    public static GameMap load(String mapDataFilePath, String imageConfigFilePath) {

        System.out.println("Inizio caricamento dati mappa da: " + mapDataFilePath);
        MapLoader rawMapData = MapLoader.load(mapDataFilePath);
        if (rawMapData == null) {
            System.err.println("GameMap Error: Impossibile caricare i dati numerici della mappa da " + mapDataFilePath);
            return null;
        }
        System.out.println("Dati mappa caricati con successo.");

        System.out.println("Inizio caricamento immagini da config: " + imageConfigFilePath);
        ImageLoader mapImageLoader = new ImageLoader();
        boolean imagesLoaded = mapImageLoader.loadImagesFromConfigFile(imageConfigFilePath);
        if (!imagesLoaded) {
            System.err.println("GameMap Error: Impossibile caricare le immagini per la mappa utilizzando la configurazione " + imageConfigFilePath);
            return null;
        }
        System.out.println("Immagini caricate con successo.");

        System.out.println("Creazione oggetto GameMap.");
        return new GameMap(rawMapData.getMapData(),
                           mapImageLoader.getLoadedImages(),
                           rawMapData.getRows(),
                           rawMapData.getCols());
    }

    public int[][] getMapData() {
        return mapData;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public BufferedImage getBlockImage(int blockValue) {
        return blockImages.get(blockValue);
    }
    public Map<Integer, BufferedImage> getBlockImages() {
        return blockImages;
    }
}