package it.unibo.runwarrior.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.runwarrior.controller.coincontroller.impl.CoinControllerImpl;
import it.unibo.runwarrior.model.Coin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class CoinTest {

    private CoinControllerImpl coinController;
    private static final String COIN_FILE_PATH = "/Coins/CoinCoordinates_map1.txt";

    @BeforeEach
    void setUp() {
        coinController = new CoinControllerImpl();
    }

    @Test
    void testCoinLoading() {
        final List<int[]> coords = coinController.loadCoinFromFile(COIN_FILE_PATH);
        assertFalse(coords.isEmpty(), "Le coordinate caricate non devono essere vuote");

        coinController.initCoinsFromFile(COIN_FILE_PATH);
        final List<Coin> coins = coinController.getCoinList();
        assertEquals(coords.size(), coins.size(), "il numero delle monete caricate deve corrispondere");
        
        for (int i = 0; i < coins.size(); i++) {
            final Coin c = coins.get(i);
            final int[] coord = coords.get(i);
            assertEquals(coord[0], c.getRow(), "Row della coin deve corrispondere");
            assertEquals(coord[1], c.getCol(), "Col della coin deve corrispondere");
            assertFalse(c.isCollected(), "la moneta non deve già essere raccolta");
            assertNotNull(c.getCoinImage(), "L'immagine della moneta deve essere caricata");
        }
    }

    @Test
    void testCoinInitiallyNotCollected() {
        final Coin coin = new Coin(2, 3);
        assertFalse(coin.isCollected(), "La moneta non dovrebbe essere raccolta inizialmente");
    }

    @Test
    void testCoinCollectChangesState() {
        final Coin coin = new Coin(1, 1);
        coin.collect();
        assertTrue(coin.isCollected(), "La moneta dovrebbe risultare raccolta dopo il collect()");
    }
}
