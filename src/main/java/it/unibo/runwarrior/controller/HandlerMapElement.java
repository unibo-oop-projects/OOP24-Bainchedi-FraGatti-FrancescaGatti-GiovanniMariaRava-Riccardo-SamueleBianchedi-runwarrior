package it.unibo.runwarrior.controller;



import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.MapElement;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.view.GameLoopPanel;

/**
 * The class uses the gamemap to render the map and setcollision, and damages
 */

public class HandlerMapElement {
    private List<MapElement> blocks;
    private int[][] map;
    private Map<Integer, BufferedImage> mapBlock;
    private int shift;
    private boolean setStart;
    private int firstY;
    private int tileSize;

    /**
    * Constructor of the class HandlerMapElement
    *
    * @param gamemap
    */
    public HandlerMapElement(final GameMap gamemap) {
        this.blocks = new ArrayList<>();
        this.mapBlock = gamemap.getBlockImages();
        this.map = gamemap.getMapData();
        MapImage();
    }

    /*
    * 0 = sky
    * 1 = grass
    * 2 = terrain
    * 3 = blocchi portale/castello
    * 4 = centro portale/castello
    * 5 = ostacolo
    * 6= presente solo nella prima
    * This method create a new MapElement using the map created in GameMap
    */
    public void MapImage() {
        for (var entry : mapBlock.entrySet()) {
            MapElement newElement = new MapElement();
            newElement.setImage(entry.getValue());
            switch (entry.getKey()) {
                case 0, 4:
                newElement.setHarmless(true);
                newElement.setCollision(false);
                break;
                case 1,2:
                newElement.setHarmless(true);
                newElement.setCollision(true);
                break;
                case 3:
                newElement.setCollision(false);
                newElement.setHarmless(true);
                newElement.setPortal(true);
                break;
                case 5,6:
                newElement.setHarmless(false);
                newElement.setCollision(true);
                break;
            }
            blocks.add(newElement);
        }
    }
    
    /**
    * @param gr 
    */
    public void printBlocks(Graphics2D gr, Character player) {
        int rows = map.length;
        int cols = map[0].length;
        shift = player.getMovementHandler().getGroundX();
        int tileHeight = GameLoopPanel.HEIGHT / rows;
        tileSize = tileHeight;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage img;
                img = this.blocks.get(map[y][x]).getImage();
                gr.drawImage(img, x * tileSize + shift, y * tileSize, tileSize, tileSize, null);
            }
        }
    }
    
    public List<MapElement> getBlocks() {
        return this.blocks;
    }
    
    public void setShift(int slide) {
        shift = slide;
    }
    
    /**
    * @return the dimension of the tile
    */
    public int getTileSize() {
        int rows = map.length;
        tileSize = GameLoopPanel.HEIGHT / rows;
        return tileSize;
    }
    
    public int getFirstY() {
        int rows = map.length;
        int cols = map[0].length;
        tileSize = GameLoopPanel.HEIGHT / rows;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (x == 0 && map[y][x] == 1 && !setStart) {
                    firstY = (y*tileSize) - (tileSize*2);
                    setStart = true;
                }
            }
        }
        return firstY;
    }
    
    /**
    * @return the list with all the obstacle's rectangles
    */
    public List<Rectangle> getCollisionRectangles() {
        List<Rectangle> collisionRects = new ArrayList<>();
        int rows = map.length;
        int cols = map[0].length;
        int tileHeight = GameLoopPanel.HEIGHT / rows;
        int tileSize = tileHeight;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (blocks.get(map[y][x]).getCollision()) {
                    Rectangle r = new Rectangle(x * tileSize, y * tileSize, tileSize, tileSize);
                    collisionRects.add(r);
                }
            }
        }
        return collisionRects;
    }
    
    public int getShift() {
        return this.shift;
    }
    
    public int[][] getMap() {
        return map;
    }

}