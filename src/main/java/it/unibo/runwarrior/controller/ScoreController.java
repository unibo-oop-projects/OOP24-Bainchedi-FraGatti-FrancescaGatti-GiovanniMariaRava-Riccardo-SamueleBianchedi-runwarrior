package it.unibo.runwarrior.controller;
import it.unibo.runwarrior.model.Score;
//import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.model.Chronometer;

public class ScoreController {
    public static final int MINIMUM_TIME_IN_SEC = 180; // ho messo 3 minuti come tempo per prendere il massimo del punteggio bonus
    public static final int MED_TIME_IN_SEC = 240;
    public static final int MAX_TIME_IN_SEC = 300;
    private final Score score; 
    private final Chronometer chronometer; 

    public ScoreController(Score score, Chronometer cronometro){
        this.chronometer = cronometro; 
        this.score = score; 
    }

    public void levelCompleted(){
        long timeUsed = chronometer.getTimeElapsed(); 
        int seconds = (int)(timeUsed/1000); 
        if(seconds <= MINIMUM_TIME_IN_SEC){
            score.increment(100); //se sta entro i 3 minuti prende 100 punti
        }
        if(seconds > MINIMUM_TIME_IN_SEC && seconds <= MED_TIME_IN_SEC){
            score.increment(50);
        }
        if(seconds > MED_TIME_IN_SEC && seconds <= MAX_TIME_IN_SEC){
            score.increment(25);
        }
        if(seconds > MAX_TIME_IN_SEC){
            score.increment(0); //se va oltre i 5 minuti non incrementa il punteggio, ha senso questo if?
        }
    }

}
