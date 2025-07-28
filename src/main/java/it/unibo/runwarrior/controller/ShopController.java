package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.model.Skin;

/**
 * ShopController interface.
 */
public interface ShopController {
    /**
     * tries the buying of premium skin.
     *
     * @return true if the purchase went good
     */
    boolean buyPremiumSkin();

    /**
     * @return default skin 
     */
    Skin getDefaultSkin();

    /**
     * @return premium skin
     */
    Skin getPremiumSkin();

    /**
     * Sets the skin selected by player.
     *
     * @param skin skin that has been selected 
     */
    void selectSkin(Skin skin);

    /**
     * @return the skin actually selected 
     */
    Skin getSelectedSkin();

    /**
     * @return true if the skin has been unlocked 
     */
    boolean isPremiumSkinUnlocked();

    /**
     * @return coin score of the player 
     */
    int getCoinScore();
}
