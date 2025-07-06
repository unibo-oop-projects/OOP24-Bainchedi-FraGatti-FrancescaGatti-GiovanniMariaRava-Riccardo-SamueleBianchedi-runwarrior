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

    public CollisionDetection(int map[][], List<MapElement> blocks, int tileSize){
        this.map = map;
        this.blocks = blocks;
        this.tileSize = tileSize;
    }

    public String checkCollision(Character player){
        playerSpeed = player.getSpeed();
        String dir = "";
        this.directions.clear();
        if(touchSolid(player.getCollisionArea().x + player.getCollisionArea().width, player.getCollisionArea().y) |
            touchSolid(player.getCollisionArea().x + player.getCollisionArea().width, player.getCollisionArea().y + tileSize) |
            touchSolid(player.getCollisionArea().x, player.getCollisionArea().y) |
            touchSolid(player.getCollisionArea().x, player.getCollisionArea().y + tileSize)){
            System.out.println(directions);
            dir = directions.stream().filter(s -> s.equals("right") | s.equals("left")).distinct().findFirst().orElse("");
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
