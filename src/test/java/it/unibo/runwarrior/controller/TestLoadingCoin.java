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
