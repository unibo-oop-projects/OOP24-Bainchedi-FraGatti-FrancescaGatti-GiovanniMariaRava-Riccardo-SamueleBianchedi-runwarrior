package it.unibo.runwarrior.model;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin {
    private int row; 
    private int col; 
    private boolean collected; 
    public BufferedImage coinImage; 

    public Coin(int row, int col){
        this.row = row; 
        this.col = col; 
        this.collected = false; 
    }
    
    public void loadCoinImage(){
        try{
                coinImage = ImageIO.read(getClass().getResourceAsStream("/Coins/CoinSmall.png"));
            }catch(IOException e){
                e.printStackTrace();
            }
    }

    public Rectangle getRectangle(int tileSize){
        return new Rectangle(col*tileSize, row*tileSize, tileSize, tileSize);
    }

    public int getRow(){
        return row; 
    }
    public int getCol(){
        return col; 
    }
    public boolean isCollected(){
        return collected; 
    }
    public void collect(){
        collected = true; 
    }
}
