package it.unibo.runwarrior.model.enemy;

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
        enemyHandler = new EnemyHandler(mockPanel);
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

}
