package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.MapElement;

public class HandlerMapElement {
    
    public static final int HEIGHT = 480;
    public static final int TILE_SIZE = 48;
    private MapElement[] blocks;
    private int[][] map;

    public HandlerMapElement(){
        blocks = new MapElement[10];
        MapImage();
    }

    public void MapImage() {
        
    } 
}
