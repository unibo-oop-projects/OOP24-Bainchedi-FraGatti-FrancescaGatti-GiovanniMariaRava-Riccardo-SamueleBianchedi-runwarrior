package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.MapElement;

public class TestHandlerMapElement {
    private HandlerMapElement handlerMapElement;
    private Map<Integer, BufferedImage> mapImage;
    private GameMap gameMap;
    private int [] [] map;
    private List<MapElement> blocks;
    //per ora testo solo il primo metood. gli altri capisco se ha veramente senso 
    /**
     * Creo una mappa più piccola e cono solo 0 e 1 così poi controllo che setti bene collisioni e danni
    */
    @BeforeEach
    public void initMap(){
        map = new int[][] {
            {0, 1, 1},
            {1, 0, 1},
            {0, 0, 1},
        };
        mapImage.put(1, null);
        mapImage.put(0, null);
        //gameMap = new GameMap(map, mapImage, 3,3);
        //handlerMapElement = new HandlerMapElement(gameMap);
    }
    @Test
    public void testCollisionSet(){

    }
    @Test
    public void testDamageSet(){

    }
}
