package it.unibo.runwarrior.model;

public interface Chronometer {

    /**
     * It starts the chronometer and saves the begginning time.
     */
    void startTimer();

    /**
     * It stops the chronometer and update the elapsed time.
     */
    public void stopTimer();

    /**
     * @return time elapsed
     */
    public long getTimeElapsed();

    /**
     * @return elapsed time formatted in string format
     */
    public String getTimeString();
}
