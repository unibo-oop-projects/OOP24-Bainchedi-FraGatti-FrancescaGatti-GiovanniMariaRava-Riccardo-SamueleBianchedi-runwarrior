package it.unibo.runwarrior.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Class that manages a chronometer that measures elapsed time
 * from a starting point. It provides functionality to start, stop and
 * get the elapsed time in both numering and formatted string formats.
 */
public class Chronometer {
    private static final int MILLIS_IN_ONE_SECOND = 1000;
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int MILLIS_IN_ONE_MINUTE = 60000; // 60_000
    private static final int MILLIS_IN_ONE_HOUR = 3600000;   // 3_600_000
    private static final int TENTHS_DIVISOR = 100;
    private long beginningTime; 
    private Timer t;
    private long timeElapsed;

    /**
     * Chronometer constructor that also inizialise the Timer.
     */
    public Chronometer() { 
        t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
               timeElapsed = System.currentTimeMillis() - beginningTime;
            }
        });
    }

    /**
     * It starts the chronometer and saves the begginning time.
     */
    public void startTimer() {
        beginningTime = System.currentTimeMillis(); 
        t.start(); 
    }

    /**
     * It stops the chronometer and update the elapsed time.
     */
    public void stopTimer() {
            timeElapsed = System.currentTimeMillis() - beginningTime;
            t.stop();
    }

    /**
     * @return time elapsed
     */
    public long getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * @return elapsed time formatted in string format
     */
    public String getTimeString() {
        final long time = getTimeElapsed();
        final int tenthsSec = (int) ((time % MILLIS_IN_ONE_SECOND) / TENTHS_DIVISOR); //decimi di secondo
        final int seconds = (int) ((time / MILLIS_IN_ONE_SECOND) % SECONDS_IN_ONE_MINUTE); 
        final int minutes = (int) (time / MILLIS_IN_ONE_MINUTE % SECONDS_IN_ONE_MINUTE); 
        final int hours = (int) (time / MILLIS_IN_ONE_HOUR); 
        //ore : minuti : secondi : decimi secondo
        return String.format("%d:%02d:%02d.%d", hours, minutes, seconds, tenthsSec);
    }
}
