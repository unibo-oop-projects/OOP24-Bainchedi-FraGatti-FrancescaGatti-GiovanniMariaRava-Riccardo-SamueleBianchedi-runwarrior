package it.unibo.runwarrior.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Chronometer {
    long beginningTime; 
    JLabel timeLabel;
    Timer t; 

    public Chronometer(JLabel l){
        timeLabel = l; 
        t = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               //azioni del timer 
               long timeDifference = System.currentTimeMillis() - beginningTime; 
               final int tenthsSec = (int) ((timeDifference % 1000) / 100); //decimi di secondo
               final int seconds = (int) ((timeDifference/1000) % 60); 
               int minutes = (int) (timeDifference/60000 % 60); 
               int hours = (int) (timeDifference / 3600000); 
               String s = String.format("%d:%2d:%2d:%d", hours, minutes, seconds, tenthsSec);
               timeLabel.setText(s);
               //ore : minuti : secondi : decimi secondo
            }
        });
    }
    public void StartTimer(){
        beginningTime = System.currentTimeMillis(); 
        t.start();
    }
    public void StopTimer(){
        t.stop();
    }
    public long getTimeElapsed(){ //metodo che chiamo in score Controller
        return System.currentTimeMillis() - beginningTime;
    }
}
