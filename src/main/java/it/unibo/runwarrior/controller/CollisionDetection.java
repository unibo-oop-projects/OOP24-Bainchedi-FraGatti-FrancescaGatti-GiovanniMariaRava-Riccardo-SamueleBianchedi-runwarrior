package it.unibo.runwarrior.controller;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.MapElement;

public class CollisionDetection {
    
    private int map[][];
    private List<MapElement> blocks = new ArrayList<>();
    private int tileSize;
    private ArrayList<String> directions = new ArrayList<>();
    private int playerSpeed;
    int feetHeadToll = 4;

    public CollisionDetection(int map[][], List<MapElement> blocks, int tileSize){
        this.map = map;
        this.blocks = blocks;
        this.tileSize = tileSize;
    }

    public String checkCollision(Character player){
        playerSpeed = player.getSpeed();
        String dir = "";
        this.directions.clear();
        if(touchSolid(player.getArea().x + player.getArea().width, player.getArea().y) |
            touchSolid(player.getArea().x + player.getArea().width, player.getArea().y + tileSize) |
            touchSolid(player.getArea().x + player.getArea().width, player.getArea().y + player.getArea().height) |
            touchSolid(player.getArea().x, player.getArea().y) |
            touchSolid(player.getArea().x, player.getArea().y + tileSize) |
            touchSolid(player.getArea().x, player.getArea().y + player.getArea().height)){
                System.out.println(directions);
                dir = directions.stream().filter(s -> s.equals("right") | s.equals("left")).distinct().findFirst().orElse("");
        }
        if(dir.isEmpty() && directions.contains("up")){
            dir = "up";
        }
        else if(dir.isEmpty() && directions.contains("down")){
            dir = "down";
        }
        return dir;
    }

    public boolean touchSolid(int x, int y){
        int[][] blockIndexes = map;
        float indexXtile = x / tileSize;
        float indexYtile = y / tileSize;
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
        int tileX = ((int) indexXtile) * tileSize;
        int tileY = ((int) indexYtile) * tileSize;
        Rectangle tileRec = new Rectangle(tileX, tileY, tileSize, tileSize);
        if(y == tileRec.y && (x >= tileRec.x && x <= tileRec.x + tileRec.width)){
            direction = "up";
        }
        else if((y == tileRec.y + tileRec.height - feetHeadToll) && (x >= tileRec.x && x <= tileRec.x + tileRec.width)){
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

    public boolean isInAir(Character player){
        if(!touchSolid(player.getArea().x + feetHeadToll, player.getArea().y + player.getArea().height) &&
            !touchSolid(player.getArea().x + player.getArea().width - feetHeadToll, player.getArea().y + player.getArea().height)){
                return true;
        }
        return false;
    }
}
