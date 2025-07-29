package it.unibo.runwarrior.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.model.chronometer.api.Chronometer;
import it.unibo.runwarrior.model.chronometer.impl.ChronometerImpl;

class ChronometerTest {

    private static final int MILLE4 = 1400;
    private static final int DUE_MILA = 2000;
    private static final int MILLE5 = 1500;
    private Chronometer chronometer;

    @BeforeEach
    void setUp() {
        chronometer = new ChronometerImpl();
    }

    @Test
    void testChronometerElapsedTimeAndFormat() throws InterruptedException {
        chronometer.startTimer();
        Thread.sleep(MILLE5);
        chronometer.stopTimer();
        final long timeElapsed = chronometer.getTimeElapsed();
        assertTrue(timeElapsed >= MILLE4 && timeElapsed <= DUE_MILA,
                   "Il tempo misurato dovrebbe essere vicino a 1500 ms");

        final String stringTime = chronometer.getTimeString();
        assertTrue(stringTime.matches("\\d+:\\d{2}:\\d{2}\\.\\d"),
                   "Il formato della stringa del timer è corretto: " + stringTime);
    }
}
