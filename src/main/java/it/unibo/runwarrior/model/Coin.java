package it.unibo.runwarrior.model;
import java.awt.Graphics2D;

public interface Coin {
    boolean isCollected(); //medoto per controllare se la moneta è stata raccolta 
    public void drawCoin(Graphics2D gr2); 
}
