package it.unibo.runwarrior.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChronometerTest {
    private static final int MILLE4 = 1400;
    private static final int DUE_MILA = 2000;
    @Test
    void testChronometerElapsedTimeAndFormat() throws InterruptedException{
        final Chronometer cr = new ChronometerImpl();
        cr.startTimer();
        Thread.sleep(1500);
        cr.stopTimer();
        final long timeElapsed = cr.getTimeElapsed();
        assertTrue(timeElapsed >= MILLE4 && timeElapsed <= DUE_MILA, "il tempo misurato dovrebbe essere vicino a 1500");
        final String stringTime = cr.getTimeString();
        assertTrue(stringTime.matches("\\d+:\\d{2}:\\d{2}\\.\\d"), "il formato della stringa del timer è corretto:" + stringTime);
    } 
}
