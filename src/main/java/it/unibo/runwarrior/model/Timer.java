package it.unibo.runwarrior.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Timer {
    long tempoInizio; 
    JLabel labelTempo;
    Timer t; 

    public Timer(JLabel l){
        labelTempo = l; 
        t = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               //azioni del timer 
               long diffTempo = System.currentTimeMillis() - tempoInizio; 
               final int decimiSec = (int) ((diffTempo % 1000) / 100); 
               final int secondi = (int) ((diffTempo/1000) % 60); 
               int minuti = (int) (diffTempo/60000 % 60); 
               int ore = (int) (diffTempo / 3600000); 
               String s = String.format("%d:%2d:%2d:%d", ore, minuti, secondi, decimiSec);
               labelTempo.setText(s);
               //ore : minuti : secondi : decimi secondo
            }
        });
    }
    public void Avvia(){
        tempoInizio = System.currentTimeMillis(); 
        t.start();
    }
    public void Ferma(){
        t.stop();
    }
    public long getTimeElapsed(){ //metodo che chiamo in score Controller
        return System.currentTimeMillis() - tempoInizio;
    }
}
