package it.unibo.runwarrior.controller;
import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.Shop;
import it.unibo.runwarrior.model.Skin;

/**
 * Class that control the shop.
 * Allows you to purchase and select skins, check the status of
 * skins and acces to coinCount via Score class.
 */
public class ShopControllerImpl implements ShopController {

    private final Score score;
    private final Shop shop;

    /**
     * Constructor of ShopControllerImpl.
     *
     * @param score score object
     */
    public ShopControllerImpl(final Score score) {
        this.score = score; 
        this.shop = new Shop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean buyPremiumSkin() {
        final Skin skin = shop.getPremiumSkin();
        if (!skin.isSkinUnlocked() && score.spendCoins(skin.getPrice())) {
           shop.unlockNewPremiumSkin();
           return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Skin getDefaultSkin() {
        return shop.getDefaultSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Skin getPremiumSkin() {
        return shop.getPremiumSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void selectSkin(final Skin skin) {
        shop.selectSkin(skin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Skin getSelectedSkin() {
        return shop.getSelectedSkin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isPremiumSkinUnlocked() {
        return shop.getPremiumSkin().isSkinUnlocked();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCoinScore() {
        return score.getCoinScore();
    }
}
