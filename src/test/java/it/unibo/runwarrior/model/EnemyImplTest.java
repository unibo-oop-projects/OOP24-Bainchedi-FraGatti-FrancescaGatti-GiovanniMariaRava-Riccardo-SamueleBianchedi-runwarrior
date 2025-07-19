package it.unibo.runwarrior.model.enemy;
/* i metodi non sono ancora ne public ne private perchè sto cercando di capire se riesco a far andare i test nel mio pc.
 * Possibile problema: non trova il classpath
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.List;

import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.view.GameLoopPanel;

public class EnemyImplTest {
    private EnemyImpl enemy;
    private GameLoopPanel glp;
    private EnemyHandler enemyHandler;
    
    /**
     * Sets up a new EnemyImpl instance.
     */
    @BeforeEach
    void setUp() {
        glp = new GameLoopPanel();
        enemyHandler = new EnemyHandler(glp);
        enemy = new EnemyImpl(50, 100, 64, 64, true, enemyHandler, glp) {
            @Override
            public void update() {
                //per i primi test non so ancora se voglio cambiargli la posizione, nei primi sicurocontrollo valori fissi
            }
        };
    }

    /**
     * Verifies that getBounds() returns the correct rectangle based on position and size.
     */
    @Test
    void testGetBounds() {
        Rectangle bounds = enemy.getBounds();
        assertEquals(50, bounds.x);
        assertEquals(100, bounds.y);
        assertEquals(16, bounds.width);
        assertEquals(16, bounds.height);
    }

    /**
     * Ensures that checkMapCollision inverts velocit
     */
    @Test
    void testCheckMapCollisionBlocksMovement() {
        Rectangle obstacle = new Rectangle(60, 100, 64, 64);
        enemy.setVelocityX(2);
        enemy.setVelocityY(0);
        enemy.checkMapCollision(List.of(obstacle));
        assertEquals(-5, enemy.getVelocityX());
        /*
         * sto cercando di fare un assert per controllare che non si infili nel blocco dal lato sinistro del blooc
         * questo non è definitivo per ora
         * assertTrue(enemy.getX() + enemy.getWidth() <= block.x);
         */
        
    }

    /**
     * questo metodo devo ancora capire se è corretto averlo qui o dentro enemyhandlertest (del quale manca ancora implementazione)
     */
    @Test 
    void testDie(){
        enemyHandler.addEnemy(enemy);
        assertEquals(1, enemyHandler.getEnemies().size());
        enemy.die();
        assertEquals(0, enemyHandler.getEnemies().size());
    }
}
