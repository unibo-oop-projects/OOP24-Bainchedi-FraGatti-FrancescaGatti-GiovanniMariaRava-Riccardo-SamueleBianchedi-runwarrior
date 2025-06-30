package it.unibo.runwarrior.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.MapElement;
import java.awt.image.BufferedImage;

public class HandlerMapElement {
    
    public static final int HEIGHT = 480;
    public static final int TILE_SIZE = 48;
    private List<MapElement> blocks;
    private int[][] map;
    private Map<Integer, BufferedImage> mapBlock;

    public HandlerMapElement(GameMap gamemap){
        blocks = new ArrayList<>();
        MapImage();
        this.mapBlock = gamemap.getBlockImages();
        this.map = gamemap.getMapData();
    }

    /**
     * 0 = sky
     * 1 = grass
     * 2 = terrain
     * 3 = blocchi portale
     * 4 = centro portale
     * 5 = ostacolo
     */
    public void MapImage() {
        for (var entry : mapBlock.entrySet()) {
            MapElement newElement = new MapElement();
            newElement.setImage(entry.getValue());
            switch (entry.getKey()) {
                case 0:
                    newElement.setHarmless(true);
                    newElement.setCollision(false);
                    break;
                case 1,2,3,4:
                    newElement.setHarmless(true);
                    newElement.setCollision(true);
                    break;
                case 5:
                    newElement.setHarmless(false);
                    newElement.setCollision(true);
                    break;
            }
            blocks.add(newElement);

        }
    } 
}
