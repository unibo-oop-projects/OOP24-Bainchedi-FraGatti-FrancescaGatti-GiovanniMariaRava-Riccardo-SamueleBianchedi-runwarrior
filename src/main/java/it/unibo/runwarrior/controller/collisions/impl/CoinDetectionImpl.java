package it.unibo.runwarrior.controller.collisions.impl;

import java.awt.Rectangle;
import java.util.Iterator;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.runwarrior.controller.coincontroller.api.CoinController;
import it.unibo.runwarrior.controller.collisions.api.CoinDetection;
import it.unibo.runwarrior.controller.score.api.ScoreController;
import it.unibo.runwarrior.model.Coin;
import it.unibo.runwarrior.model.player.api.Character;

/**
 * Class that detects coins collection.
 */
public class CoinDetectionImpl implements CoinDetection {

    private final int tileSize;
    private final CoinController coinC;
    private final ScoreController score;

    /**
     * Constructor of coin detection to link with CoinController and ScoreController.
     *
     * @param tileSize size of tiles
     * @param coinC CoinController instance
     * @param score ScoreController instance
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public CoinDetectionImpl(final int tileSize, final CoinController coinC, final ScoreController score) {
        this.tileSize = tileSize;
        this.coinC = coinC;
        this.score = score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlCoinCollision(final Character player) {
        final Rectangle playerRectangle = player.getArea();
        final Iterator<Coin> it = coinC.getCoinList().iterator();
        while (it.hasNext()) {
            final Coin coin = it.next();
            if (!coin.isCollected()) {
                final Rectangle coinRectangle = coin.getRectangle(tileSize);
                if (playerRectangle.intersects(coinRectangle)) {
                    coin.collect();
                    coinC.increaseCoinsCollected();
                    if (score != null) {
                        score.addCoin();
                    }
                    it.remove();
                }
            }
        }
    }
}
