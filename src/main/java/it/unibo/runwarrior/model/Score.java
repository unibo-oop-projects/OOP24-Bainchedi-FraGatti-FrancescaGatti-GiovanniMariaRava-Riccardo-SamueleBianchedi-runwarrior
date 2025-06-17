package it.unibo.runwarrior.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {
    private int score; 
    private final String SAVE_FILE = "fileScore.txt"; //file di testo in resources su cui salvo i dati della sessione di gioco
    public Score(){
        this.score = loadScore(); 
    }

    public void increment(int points){ //mi aggiorna il punteggio (quindi da chiamare sia per il timer che per le monete)
        this.score += points; 
        saveScore();
    }

    public int getScore(){
        return score; 
    }

    public void reset(){ //da chiamare quando il giocatore non vuole salvare i dati della partita
        this.score = 0; 
    }

    public void printScore(){
        System.out.println("punteggio partita attuale: " + score);
    }

    //idea: ogni volta che finisce una partita mi salva score in un file di testo esterno
    private void saveScore(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))){
            writer.write(String.valueOf(score));
        } catch(IOException e){
            System.err.println("errore salvataggio del punteggio");
        }
    }

    private int loadScore(){
        try(BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))){
            return Integer.parseInt(reader.readLine());
        } catch(IOException e){ //tipo se non trova il file o altro
            return 0;
        }
    }
}

