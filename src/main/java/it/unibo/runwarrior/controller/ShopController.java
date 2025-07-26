package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.Shop;
import it.unibo.runwarrior.model.Skin;

public class ShopController {
    private final Score score;
    private final Shop shop;
    
    public ShopController(Score score) {
        this.score = score; 
        this.shop = new Shop();
    }

    public boolean buyPremiumSkin() {
        Skin skin = shop.getPremiumSkin();
        if (!skin.isSkinUnlocked() && score.spendCoins(skin.getPrice())) {
           shop.unlockNewPremiumSkin();
           return true;
        }
        return false;
    }

    public Skin getDefaultSkin() {
        return shop.getDefaultSkin();
    }

    public Skin getPremiumSkin() {
        return shop.getPremiumSkin();
    }

    public void selectSkin(Skin skin){
        shop.selectSkin(skin);
    }

    public Skin getSelectedSkin() {
        return shop.getSelectedSkin();
    }

    public boolean isPremiumSkinUnlocked() {
        return shop.getPremiumSkin().isSkinUnlocked();
    }

    public int getCoinScore() {
        return score.getCoinScore();
    }
}
