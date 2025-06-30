package it.unibo.runwarrior.model;

public class Coin {
    private int row; 
    private int col; 
    private boolean collected; 

    public Coin(int row, int col){
        this.row = row; 
        this.col = col; 
        this.collected = false; 
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
