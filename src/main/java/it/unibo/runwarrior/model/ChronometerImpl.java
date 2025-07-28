package it.unibo.runwarrior.model;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that manages a chronometer that measures elapsed time
 * from a starting point. It provides functionality to start, stop and
 * get the elapsed time in both numering and formatted string formats.
 */
public class ChronometerImpl implements Chronometer {
    private static final int MILLIS_IN_ONE_SECOND = 1000;
    private static final int SECONDS_IN_ONE_MINUTE = 60;
    private static final int MILLIS_IN_ONE_MINUTE = 60_000; // 60_000
    private static final int MILLIS_IN_ONE_HOUR = 3_600_000;   // 3_600_000
    private static final int TENTHS_DIVISOR = 100;
    private long beginningTime; 
    private final Timer t;
    private long timeElapsed;

    /**
     * Chronometer constructor that also inizialise the Timer.
     */
    public ChronometerImpl() { 
        t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
               timeElapsed = System.currentTimeMillis() - beginningTime;
            }
        });
    }

    @Override
    public final void startTimer() {
        beginningTime = System.currentTimeMillis(); 
        t.start(); 
    }

    @Override
    public final void stopTimer() {
            timeElapsed = System.currentTimeMillis() - beginningTime;
            t.stop();
    }

    @Override
    public final long getTimeElapsed() {
        return timeElapsed;
    }

    @Override
    public final String getTimeString() {
        final long time = getTimeElapsed();
        final int tenthsSec = (int) (time % MILLIS_IN_ONE_SECOND / TENTHS_DIVISOR); //decimi di secondo
        final int seconds = (int) (time / MILLIS_IN_ONE_SECOND % SECONDS_IN_ONE_MINUTE); 
        final int minutes = (int) (time / MILLIS_IN_ONE_MINUTE % SECONDS_IN_ONE_MINUTE); 
        final int hours = (int) (time / MILLIS_IN_ONE_HOUR); 
        //ore : minuti : secondi : decimi secondo
        return String.format("%d:%02d:%02d.%d", hours, minutes, seconds, tenthsSec);
    }
}
