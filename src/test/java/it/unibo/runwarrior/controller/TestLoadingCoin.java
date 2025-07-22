// package it.unibo.runwarrior.controller;
// import it.unibo.runwarrior.model.player.Character;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import java.util.List;

// import org.junit.jupiter.api.Test;

// public class TestLoadingCoin {
//     @Test
//     public void testLoadCoinFromFile(){
//         Character player = new 
//         CoinController coinController = new CoinController();
//         List<int[]> coords = coinController.loadCoinFromFile("\\Coins\\CoinCoordinates_map1.txt");

//         assertNotNull(coords, "La lista delle coordinate non deve essere null");
//         assertFalse(coords.isEmpty(), "La lista delle coordinate non deve essere vuota");

//         // Controlla la prima coordinata (modifica i valori in base al tuo file)
//         int[] firstCoord = coords.get(0);
//         assertEquals(16, firstCoord[0], "La prima riga della prima moneta dovrebbe essere 0");
//         assertEquals(4, firstCoord[1], "La prima colonna della prima moneta dovrebbe essere 1");

//     }
// }
package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.Coin;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestLoadingCoin {

    private static final int TILE_SIZE = 48;
    private static final int SCREEN_WIDTH = 768; // oppure GameLoopPanel.WIDTH;

    @Test
    public void testMoneteVisibiliDuranteLoScroll() {
        // Simula 3 monete su mappa
        List<Coin> coinList = new ArrayList<>();
        coinList.add(new Coin(16, 4));   // x = 4 * 48 = 192
        coinList.add(new Coin(16, 15));  // x = 15 * 48 = 720
        coinList.add(new Coin(16, 20));  // x = 20 * 48 = 960

        // Prova groundX da 0 a 1000 con passo 100
        for (int groundX = 0; groundX <= 1000; groundX += 100) {
            System.out.println("GROUND X: " + groundX);
            for (Coin coin : coinList) {
                int mondoX = coin.getCol() * TILE_SIZE;
                int screenX = mondoX - groundX;

                boolean visibile = screenX + TILE_SIZE > 0 && screenX < SCREEN_WIDTH;
                System.out.printf("Coin at col=%d --> mondoX=%d --> screenX=%d --> visibile=%b\n",
                    coin.getCol(), mondoX, screenX, visibile);
            }
            System.out.println("-----------------------------");
        }

        // Assert di esempio: la moneta alla colonna 4 è visibile quando groundX = 0
        Coin firstCoin = coinList.get(0);
        int screenX = firstCoin.getCol() * TILE_SIZE - 0;
        assertTrue(screenX < SCREEN_WIDTH, "La prima moneta dovrebbe essere visibile a groundX = 0");
    }
}