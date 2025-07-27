package it.unibo.runwarrior.controller;

import java.util.List;

import it.unibo.runwarrior.controller.enemy.EnemySpawner;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.model.player.NakedWizard;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpManager;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;
import it.unibo.runwarrior.view.enemy.impl.GoblinView;
import it.unibo.runwarrior.view.enemy.impl.GuardView;
import it.unibo.runwarrior.view.enemy.impl.MonkeyView;
import it.unibo.runwarrior.view.enemy.impl.SnakeView;
import it.unibo.runwarrior.view.enemy.impl.WizardView;

public class GameLoopController {
    
    private GameLoopPanel glp;
    private Character player;
    private CharacterComand commands;
    private PowersHandler powerUpsHandler;
    private PowerUpManager powersManager;

    private HandlerMapElement mapHandler;
    private EnemyHandlerImpl enemyHandler;
    private EnemyViewFactoryImpl enemyViewFactory;
    private EnemySpawner enemySpawner;
    private GameMap gameMap;
    private CoinController coinController;
    private Score score;
    private ScoreController scoreController;

    public GameLoopController(String mapPath, String themePath, String enemiesPath, String coinsPath) {
        this.gameMap = GameMap.load(mapPath, themePath);
        this.coinController = new CoinController();
        List<int[]> coords = coinController.loadCoinFromFile(coinsPath);
        for(int[] coord : coords){
            coinController.addCoins(coord[0], coord[1]);
        }
        
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.powersManager = new PowerUpManager(this, mapHandler, gameMap.getMapData());
        this.powerUpsHandler = new PowersHandler(this, commands, mapHandler, powersManager);
        this.enemyViewFactory = new EnemyViewFactoryImpl();
        initializeEnemyViewFactory();
        this.enemyHandler = new EnemyHandlerImpl(this, this.enemyViewFactory);
        this.enemySpawner = new EnemySpawner(enemyHandler, this);
        enemySpawner.loadEnemiesFromStream(getClass().getResourceAsStream(enemiesPath));

        this.score = new Score(GameSaveManager.getInstance());
        this.scoreController = new ScoreController(score);
        this.coinController.setScoreController(scoreController);
        this.glp = new GameLoopPanel(mapPath, themePath, enemiesPath, coinsPath, this);
        initializePlayer();
    }

    public void update() {
        player.update();
        enemySpawner.update();
        enemyHandler.updateWithMap(mapHandler.getCollisionRectangles());
    }

    /**
     * Chooses one of the two player with whom the game starts.
     * To be connected with the shop
     */
    public void initializePlayer() {
        final String selectedSkin = GameSaveManager.getInstance().getSelectedSkinName();
        final boolean wizardUnlocked = GameSaveManager.getInstance().isSkinPremiumSbloccata();
        if ("WIZARD".equalsIgnoreCase(selectedSkin) && wizardUnlocked) {
            player = new NakedWizard(this, commands, mapHandler, powersManager);
        } else {
            player = new NakedWarrior(this, commands, mapHandler, powersManager);
        }
        player.getMovementHandler().setStartY(mapHandler.getFirstY());
        powerUpsHandler.setIndex();
    }

    public Character getPlayer() {
        return this.player;
    }

    public void setPlayer(Character pl, int realX, int x, int y, int shift, long lastHit) {
        this.player = pl;
        this.player.getMovementHandler().setLocationAfterPowerup(x, y, realX, shift, lastHit);
        this.coinController.updatePlayer(pl);
    }

    public GameLoopPanel getGlp() {
        return this.glp;
    }

    public CharacterComand getCommands() {
        return this.commands;
    }

    public PowersHandler getPowersHandler() {
        return this.powerUpsHandler;
    }

    public PowerUpManager getPowersManager() {
        return this.powersManager;
    }
    
    public HandlerMapElement getMapHandler() {
        return this.mapHandler;
    }

    public EnemyHandlerImpl getEnemyHandler() {
        return this.enemyHandler;
    }

    public CoinController getCoinController() {
        return this.coinController;
    }

    public ScoreController getScoreController() {
        return this.scoreController;
    }

    /**
     * Map the int type with the correct EnemyView
     */
    private final void initializeEnemyViewFactory() {
        enemyViewFactory.register(1, new GuardView(this));
        enemyViewFactory.register(2, new SnakeView(this));
        enemyViewFactory.register(3, new WizardView(this));
        enemyViewFactory.register(4, new GoblinView(this));
        enemyViewFactory.register(5, new MonkeyView(this));
    }
}
