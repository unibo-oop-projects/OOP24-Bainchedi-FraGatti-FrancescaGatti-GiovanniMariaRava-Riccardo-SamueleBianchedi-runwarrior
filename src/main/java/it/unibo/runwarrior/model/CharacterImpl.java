package it.unibo.runwarrior.model;

import java.awt.Graphics2D;

import it.unibo.runwarrior.view.GameLoopPanel;

public class CharacterImpl implements Character{

    public static final int SIZE_CHARACTER = 96;
    public static final int START_Y = 387;
    public static final int START_X = 320;
    public static final int maxScreenX = 550;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    public static final int minScreenX = 180;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO

    private int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    private int playerY = START_Y;// * VERTICALE
    private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int screenY = START_Y;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)

    public GameLoopPanel glp;

    public CharacterImpl(GameLoopPanel panel){
        this.glp = panel;
    }

    @Override
    public void update() {
    }

    @Override
    public void drawPlayer(Graphics2D gr2) {
    }

    @Override
    public void playerImage() {
    }

    @Override
    public void setLocationAfterPowerup(int x, int y, int realx) {
    }

    @Override
    public int getPlX() {
        return this.playerX;
    }

    @Override
    public int getPlY() {
        return this.playerY;
    }

    @Override
    public int getScX() {
        return this.screenX;
    }

    @Override
    public int getScY() {
        return this.screenY;
    }
    
}
