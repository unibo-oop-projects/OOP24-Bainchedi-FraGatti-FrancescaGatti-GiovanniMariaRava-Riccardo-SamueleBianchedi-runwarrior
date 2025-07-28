package it.unibo.runwarrior.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChronometerTest {
    @Test
    void testChronometerElapsedTimeAndFormat() throws InterruptedException{
        final Chronometer cr = new ChronometerImpl();
        cr.startTimer();
        Thread.sleep(1500);
        cr.stopTimer();
        final long timeElapsed = cr.getTimeElapsed();
        assertTrue(timeElapsed >= 1400 && timeElapsed <= 2000, "il tempo misurato dovrebbe essere vicino a 1500");
        final String stringTime = cr.getTimeString();
        assertTrue(stringTime.matches("\\d+:\\d{2}:\\d{2}\\.\\d"), "il formato della stringa del timer è corretto:" + stringTime);
    } 
}
