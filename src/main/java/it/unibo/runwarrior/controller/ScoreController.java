package it.unibo.runwarrior.controller;
import it.unibo.runwarrior.model.Score;
//import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.model.Chronometer;

public class ScoreController {
    public static final int MINIMUM_TIME_IN_SEC = 180; // ho messo 3 minuti come tempo per prendere il massimo del punteggio bonus
    public static final int MED_TIME_IN_SEC = 240;
    public static final int MAX_TIME_IN_SEC = 300;
    public static final int SMALL_COIN_VALUE = 100;
    public static final int BIG_COIN_VALUE = 400; 
    private final Score score; 
    private final Chronometer chronometer; 
    public int coinPoints; 

    public ScoreController(Score score, Chronometer cronometro){
        this.chronometer = cronometro; 
        this.score = score; 
    }

    public void levelCompleted(int coinSmallCount, int coinBigCount){
    }

}
