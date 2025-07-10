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
    private int feetHeadToll = 5;

    public CollisionDetection(int map[][], List<MapElement> blocks, int tileSize){
        this.map = map;
        this.blocks = blocks;
        this.tileSize = tileSize;
    }

    public String checkCollision(Character player){
        playerSpeed = player.getSpeed();
        String dir = "";
        this.directions.clear();
        if(touchSolid(player.getArea().x + player.getArea().width - (feetHeadToll+1), player.getArea().y, player, true) |
            touchSolid(player.getArea().x + player.getArea().width, player.getArea().y + player.getArea().height/2, player, true) |
            touchSolid(player.getArea().x + player.getArea().width, player.getArea().y + player.getArea().height, player, true) |
            touchSolid(player.getArea().x + (feetHeadToll+1), player.getArea().y, player, true) |
            touchSolid(player.getArea().x, player.getArea().y + player.getArea().height/2, player, true) |
            touchSolid(player.getArea().x, player.getArea().y + player.getArea().height, player, true)){
                dir = directions.stream().filter(s -> s.equals("right") | s.equals("left"))
                        .distinct().findFirst().orElse("");
        }
        System.out.println(directions);
        if(dir.isEmpty() && directions.contains("up")){
            dir = "up";
        }
        if(directions.contains("down")){
            dir = "down";
        }
        return dir;
    }

    public boolean touchSolid(int x, int y, Character player, boolean checkDirections){
        int[][] blockIndexes = map;
        float indexXtile = x / tileSize;
        float indexYtile = y / tileSize;
        int blockIndex = blockIndexes[(int) indexYtile][(int) indexXtile];
        if(blocks.get(blockIndex).getCollision() || y <= 0){
            if(checkDirections){
                this.directions.add(checkCollisionDirection(x, y, indexXtile, indexYtile, player));
            }
            return true;
        }
        else{
            for(int i = player.getArea().width; i >= 0; i = i - player.getArea().width){
                indexXtile = (player.getArea().x + i) / tileSize;
                indexYtile = player.getArea().y / tileSize;
                blockIndex = blockIndexes[(int) indexYtile][(int) indexXtile];
                if(blocks.get(blockIndex).getCollision()){
                    if(checkDirections){
                        String tempDir = checkCollisionDirection(x, y, indexXtile, indexYtile, player);
                        if(tempDir.equals("right") || tempDir.equals("left")){
                            this.directions.add(tempDir);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public String checkCollisionDirection(int x, int y, float indexXtile, float indexYtile, Character player){
        String direction = "";
        int tileX = ((int) indexXtile) * tileSize;
        int tileY = ((int) indexYtile) * tileSize;
        Rectangle tileRec = new Rectangle(tileX, tileY, tileSize, tileSize);
        System.out.println(x + " " + y);
        if(y == tileRec.y && (x >= tileRec.x && x <= tileRec.x + tileRec.width)){
            direction = "up";
        }
        else if(isInAir(player) && y == player.getArea().y && (tileRec.y + tileRec.height - y) < feetHeadToll &&
                 (x >= tileRec.x && x <= tileRec.x + tileRec.width) || y <= 0){
                direction = "down";
        }
        else if(x - playerSpeed <= tileRec.x){
            direction = "right";
        }
        else if(x + playerSpeed >= tileRec.x + tileRec.width){
            direction = "left";
        }
        return direction;
    }

    public boolean isInAir(Character player){
        if(!touchSolid(player.getArea().x + feetHeadToll, player.getArea().y + player.getArea().height, player, false) &&
            !touchSolid(player.getArea().x + player.getArea().width - feetHeadToll, player.getArea().y + player.getArea().height, player, false)){
                return true;
        }
        return false;
    }
}