package it.unibo.runwarrior.model.chronometer.api;

/**
 * Chronometer interface.
 */
public interface Chronometer {

    /**
     * It starts the chronometer and saves the begginning time.
     */
    void startTimer();

    /**
     * It stops the chronometer and update the elapsed time.
     */
    void stopTimer();

    /**
     * @return time elapsed
     */
    long getTimeElapsed();

    /**
     * @return elapsed time formatted in string format
     */
    String getTimeString();
}
