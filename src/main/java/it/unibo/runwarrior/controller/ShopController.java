package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.Shop;
import it.unibo.runwarrior.model.Skin;

/**
 * Class that control the shop.
 * Allows you to purchase and select skins, check the status of
 * skins and acces to coinCount via Score class.
 */
public class ShopController {
    private final Score score;
    private final Shop shop;

    /**
     * Constructor of ShopController.
     *
     * @param score score object
     */
    public ShopController(final Score score) {
        this.score = score; 
        this.shop = new Shop();
    }

    /**
     * tries the buying of premium skin.
     *
     * @return true if the purchase went good
     */
    public final boolean buyPremiumSkin() {
        final Skin skin = shop.getPremiumSkin();
        if (!skin.isSkinUnlocked() && score.spendCoins(skin.getPrice())) {
           shop.unlockNewPremiumSkin();
           return true;
        }
        return false;
    }

    /**
     * @return default skin 
     */
    public final Skin getDefaultSkin() {
        return shop.getDefaultSkin();
    }

    /**
     * @return premium skin
     */
    public final Skin getPremiumSkin() {
        return shop.getPremiumSkin();
    }

    /**
     * Sets the skin selected by player.
     *
     * @param skin skin that has been selected 
     */
    public final void selectSkin(final Skin skin) {
        shop.selectSkin(skin);
    }

    /**
     * @return the skin actually selected 
     */
    public final Skin getSelectedSkin() {
        return shop.getSelectedSkin();
    }

    /**
     * @return true if the skin has been unlocked 
     */
    public final boolean isPremiumSkinUnlocked() {
        return shop.getPremiumSkin().isSkinUnlocked();
    }

    /**
     * @return coin score of the player 
     */
    public final int getCoinScore() {
        return score.getCoinScore();
    }
}
