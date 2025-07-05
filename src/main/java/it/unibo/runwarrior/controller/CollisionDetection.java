package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.MapElement;

public class CollisionDetection {
    
    private int map[][];
    private List<MapElement> blocks = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();
    private int playerSpeed;

    public CollisionDetection(int map[][], List<MapElement> blocks){
        this.map = map;
        this.blocks = blocks;
    }

    public String checkCollision(Character player){

        return "";
    }

    public boolean touchSolid(int x, int y){
        int[][] blockIndexes = map;
        float indexXtile = x / 48;
        float indexYtile = y / 48;
        int blockIndex = blockIndexes[(int) indexYtile][(int) indexXtile];
        if(blocks.get(blockIndex).getCollision()){
            this.directions.add(checkCollisionDirection(x, y, indexXtile, indexYtile));
            return true;
        }
        else{
            return false;
        }
    }

    public String checkCollisionDirection(int x, int y, float indexXtile, float indexYtile){
        String direction = "";
        int tileX = ((int) indexXtile) * 48;
        int tileY = ((int) indexYtile) * 48;
        Rectangle tileRec = new Rectangle(tileX, tileY, 48, 48);
        if(y == tileRec.y && (x >= tileRec.x && x <= tileRec.x + tileRec.width)){
            direction = "up";
        }
        else if((y == tileRec.y + tileRec.height - 1) && (x >= tileRec.x && x <= tileRec.x + tileRec.width)){
            direction = "down";
        }
        else if(x - playerSpeed < tileRec.x){
            direction = "right";
        }
        else if(x + playerSpeed > tileRec.x + tileRec.width){
            direction = "left";
        }
        return direction;
    }
}
