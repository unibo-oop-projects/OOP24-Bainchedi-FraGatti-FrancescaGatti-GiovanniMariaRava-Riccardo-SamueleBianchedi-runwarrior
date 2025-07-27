package it.unibo.runwarrior.controller.collisions;

import java.awt.Rectangle;
import java.util.Iterator;

import it.unibo.runwarrior.controller.CoinController;
import it.unibo.runwarrior.controller.ScoreController;
import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.model.player.Character;

public class CoinDetectionImpl {

    private int tileSize;
    private CoinController coinC;
    private ScoreController score;

    public CoinDetectionImpl(final int tileSize, final CoinController coinC, final ScoreController score) {
        this.tileSize = tileSize;
        this.coinC = coinC;
        this.score = score;
    }
    
    public void controlCoinCollision(final Character player) {
        Rectangle playerRectangle = player.getArea();
        Iterator<Coin> it = coinC.getCoinList().iterator();
        while(it.hasNext()) {
            Coin coin = it.next();
            if (!coin.isCollected()) {
                Rectangle coinRectangle = coin.getRectangle(tileSize);
                if (playerRectangle.intersects(coinRectangle)) {
                    coin.collect();
                    coinC.increaseCoinsCollected();
                    if(score != null){
                        score.addCoin();
                    }
                    it.remove();
                }
            }
        }
    }
}
