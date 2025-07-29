package it.unibo.runwarrior.controller;

import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;

import it.unibo.runwarrior.controller.coinController.api.CoinController;
import it.unibo.runwarrior.controller.coinController.impl.CoinControllerImpl;
import it.unibo.runwarrior.controller.enemy.EnemySpawner;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.controller.player.CharacterComand;
import it.unibo.runwarrior.controller.score.api.ScoreController;
import it.unibo.runwarrior.controller.score.impl.ScoreControllerImpl;
import it.unibo.runwarrior.controller.shop.api.ShopController;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.model.player.NakedWizard;
import it.unibo.runwarrior.model.save.GameSaveManager;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;
import it.unibo.runwarrior.view.enemy.impl.GoblinView;
import it.unibo.runwarrior.view.enemy.impl.GuardView;
import it.unibo.runwarrior.view.enemy.impl.MonkeyView;
import it.unibo.runwarrior.view.enemy.impl.SnakeView;
import it.unibo.runwarrior.view.enemy.impl.WizardView;

/**
 * Controller del ciclo principale di gioco.
 * Gestisce l'inizializzazione dei campi necessari e gestisce l'aggiornamento.
 */
public class GameLoopController {
    private static final int TYPE_FIVE = 5;
    private GameLoopPanel glp;
    private Character player;
    private CharacterComand commands;
    private PowersHandler powerUpsHandler;
    private PowerUpController powerController;
    private PowerUpManager powersManager;

    private HandlerMapElement mapHandler;
    private EnemyHandlerImpl enemyHandler;
    private EnemyViewFactoryImpl enemyViewFactory;
    private EnemySpawner enemySpawner;
    private GameMap gameMap;
    private CoinController coinController;
    private Score score;
    private ScoreController scoreController;
    private int levelIndex;
    private ShopController shopController;

    /**
     * Constructor of the class. 
     *
     * @param mainFrame is the frame in which menu is shown
     * @param mapPath the path for loading of the map
     * @param themePath the path for loading the images of tile
     * @param enemiesPath the path for loadin the enemies position
     * @param coinsPath the path for loading the coin position
     */
    public GameLoopController(final JFrame mainFrame, final String mapPath, final String themePath,
                                final String enemiesPath, final String coinsPath) {
        this.gameMap = GameMap.load(mapPath, themePath);
        this.coinController = new CoinControllerImpl();
        this.levelIndex = calculateLevelIndex(mapPath);
        final List<int[]> coords = coinController.loadCoinFromFile(coinsPath);
        for (final int[] coord : coords) {
            coinController.addCoins(coord[0], coord[1]);
        }
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.powerController = new PowerUpController(this, mapHandler, gameMap.getMapData());
        this.powersManager = new PowerUpManager(powerController.getPowerUps(), mapHandler);
        this.powerUpsHandler = new PowersHandler(this, commands, mapHandler, powerController);
        this.enemyViewFactory = new EnemyViewFactoryImpl();
        initializeEnemyViewFactory();
        this.enemyHandler = new EnemyHandlerImpl(this, this.enemyViewFactory);
        this.enemySpawner = new EnemySpawner(enemyHandler, this);
        enemySpawner.loadEnemiesFromStream(getClass().getResourceAsStream(enemiesPath));

        this.score = new Score();
        this.scoreController = new ScoreControllerImpl(score);
        //this.coinController.setScoreController(scoreController);
        this.glp = new GameLoopPanel(mainFrame, mapPath, themePath, enemiesPath, coinsPath, this);
        initializePlayer();
    }

    /**
     * Update the state of the game in the order: player, enemySpawner, enemyHandler.
     */
    public void update() {
        player.update();
        enemySpawner.update();
        enemyHandler.updateWithMap(mapHandler.getCollisionRectangles());
    }

    /**
     * Chooses one of the two player with whom the game starts.
     * To be connected with the shop
     */
    private void initializePlayer() {
        final String selectedSkin = GameSaveManager.getInstance().getSelectedSkinName();
        final boolean wizardUnlocked = GameSaveManager.getInstance().isSkinPremiumSbloccata();
        if ("WIZARD".equalsIgnoreCase(selectedSkin) && wizardUnlocked) {
            player = new NakedWizard(this, commands, mapHandler, powerController);
        } else {
            player = new NakedWarrior(this, commands, mapHandler, powerController);
        }
        player.getMovementHandler().setStartY(mapHandler.getFirstY());
        powerUpsHandler.setIndex();
    }

    /**
     * Getters for the main player.
     *
     * @return the player
     */
    public Character getPlayer() {
        return this.player;
    }

    /**
     * Sets the current player in the panel.
     *
     * @param pl current player
     * @param realX x position in the map
     * @param x x position in the screen
     * @param y y position in the screen
     * @param shift variable to slide map
     * @param lastHit handles immortality time
     */
    public void setPlayer(final Character pl, final int realX, final int x, final int y, final int shift, final long lastHit) {
        this.player = pl;
        this.player.getMovementHandler().setLocationAfterPowerup(x, y, realX, shift, lastHit);
        this.coinController.updatePlayer(pl);
    }

    /**
     * Return the main gameLoopPanel in which everything is shown.
     *
     * @return GameLoopPanel
     */
    public GameLoopPanel getGlp() {
        return this.glp;
    }

    /**
     * @return the current set of character commands
     */
    public CharacterComand getCommands() {
        return this.commands;
    }

    /**
     * @return the handler that controls power‑ups
     */
    public PowersHandler getPowersHandler() {
        return this.powerUpsHandler;
    }

    /**
     * @return the manager responsible for rendering power‑ups
     */
    public PowerUpManager getPowersManager() {
        return this.powersManager;
    }

    /**
     * @return the map handler that provides tiles and collision list
     */
    public HandlerMapElement getMapHandler() {
        return this.mapHandler;
    }

    /**
     * @return the component that controls enemies
     */
    public EnemyHandlerImpl getEnemyHandler() {
        return this.enemyHandler;
    }

    /**
     * @return the controller that manages coins on the map
     */
    public CoinController getCoinController() {
        return this.coinController;
    }

    /**
     * @return the controller that updates the score
     */
    public ScoreController getScoreController() {
        return this.scoreController;
    }

    /**
     * Map the int type with the correct EnemyView.
     */
    private void initializeEnemyViewFactory() {
        enemyViewFactory.register(1, new GuardView(this));
        enemyViewFactory.register(2, new SnakeView(this));
        enemyViewFactory.register(3, new WizardView(this));
        enemyViewFactory.register(4, new GoblinView(this));
        enemyViewFactory.register(TYPE_FIVE, new MonkeyView(this));
    }

    /**
     * @param mapPath the path for loading of the map
     * @return the number of the current level
     */
    private int calculateLevelIndex(final String mapPath) {
        final String lowerCasePath = mapPath.toLowerCase(Locale.ROOT);
        if (lowerCasePath.contains("map_1") || lowerCasePath.contains("map1")) {
            return 1;
        } else if (lowerCasePath.contains("map_2") || lowerCasePath.contains("map2")) {
            return 2;
        } else if (lowerCasePath.contains("map_3") || lowerCasePath.contains("map3")) {
            return 3;
        } else {
            return 1;
        }
}

    /**
     * @return the current level 
     */
    public final int getCurrentLevel() {
        return this.levelIndex;
    }

    /**
     * @return the controller for the shop 
     */
    public final ShopController getShopController() {
        return this.shopController;
    }
}
