package it.unibo.runwarrior.view;

import java.awt.Graphics;

import it.unibo.runwarrior.controller.CoinController;

public class CoinView {
    private final CoinController coinController;
    
    public CoinView(CoinController coinController){
        this.coinController = coinController;
    }

    public void drawCoins(Graphics g, int tileSize){
        coinController.drawAllCoins(g,tileSize);
    }
}
