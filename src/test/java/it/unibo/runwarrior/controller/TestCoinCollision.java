package it.unibo.runwarrior.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCoinCollision {

    static class Coin {
        private int row, col;
        private boolean collected = false;
        private final int size = 20;

        public Coin(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Rectangle getRectangle() {
            return new Rectangle(col * size, row * size, size, size);
        }

        public boolean isCollected() {
            return collected;
        }

        public void collect() {
            collected = true;
        }
    }

    static class Player {
        private Rectangle area;

        public Player(int x, int y, int width, int height) {
            area = new Rectangle(x, y, width, height);
        }

        public Rectangle getArea() {
            return area;
        }
    }

    List<Coin> coinList;
    Player player;

    @BeforeEach
    void setup() {
        coinList = new ArrayList<>();
        coinList.add(new Coin(4, 4)); // pos: (80,80)
        coinList.add(new Coin(8, 8)); // pos: (160,160)
        player = new Player(80, 80, 40, 40); // pos player covers (80,80)
    }

    void controlCoinCollision() {
        Rectangle playerRect = player.getArea();
        for (Coin coin : coinList) {
            if (!coin.isCollected()) {
                Rectangle coinRect = coin.getRectangle();
                if (playerRect.intersects(coinRect)) {
                    coin.collect();
                }
            }
        }
    }

    @Test
    void testCoinCollision() {
        // Prima della collisione nessuna moneta è raccolta
        for (Coin c : coinList) {
            assertFalse(c.isCollected());
        }

        controlCoinCollision();

        // La prima moneta (80,80) dovrebbe essere raccolta
        assertTrue(coinList.get(0).isCollected());

        // La seconda no, è lontana
        assertFalse(coinList.get(1).isCollected());
    }
}