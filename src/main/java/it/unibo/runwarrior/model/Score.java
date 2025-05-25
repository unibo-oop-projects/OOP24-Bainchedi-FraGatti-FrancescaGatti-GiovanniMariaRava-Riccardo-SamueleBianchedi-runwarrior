package it.unibo.runwarrior.model;

public class Score {
    private int value; 

    public Score(){
        this.value = 0; 
    }

    public void increment(int points){
        this.value += points; 
    }

    public int getValue(){
        return value; 
    }

    public void reset(){
        this.value = 0; 
    }

}
