package it.unibo.runwarrior.model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Chronometer {
    long beginningTime; 
    JLabel timeLabel;
    Timer t; 
    private long timeElapsed = 0;

    public Chronometer(){ 
        t = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               timeElapsed = System.currentTimeMillis() - beginningTime;
               
            }
        });
    }
    public void StartTimer(){
        beginningTime = System.currentTimeMillis(); 
        t.start(); 
    }
    public void StopTimer(){
            timeElapsed = System.currentTimeMillis() - beginningTime;
            t.stop();
    }
    public long getTimeElapsed(){ //metodo che chiamo in score Controller
        return timeElapsed;
    }
    public String getTimeString(){
        long time = getTimeElapsed();
        final int tenthsSec = (int) ((time % 1000) / 100); //decimi di secondo
        final int seconds = (int) ((time/1000) % 60); 
        int minutes = (int) (time/60000 % 60); 
        int hours = (int) (time / 3600000); 
         //ore : minuti : secondi : decimi secondo
        return String.format("%d:%02d:%02d.%d", hours, minutes, seconds, tenthsSec);
    }
}
