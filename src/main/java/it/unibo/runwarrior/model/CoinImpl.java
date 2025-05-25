package it.unibo.runwarrior.model;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//import java.io.File;

//import javax.imageio.ImageIO;

public abstract class CoinImpl implements Coin {
    private boolean collected; 
   
    protected BufferedImage coinImage;
    public BufferedImage getCoinImage() {
        return coinImage;
    }

    public void setCoinImage(BufferedImage coinImage) {
        this.coinImage = coinImage;
    }

    @Override
    public boolean isCollected(){
        return collected; 
    }

    @Override
    public void drawCoin(Graphics2D gr2){
        
    }
}
