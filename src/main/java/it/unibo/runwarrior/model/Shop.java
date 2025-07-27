package it.unibo.runwarrior.model;

/**
 * Represents the shop containing skins available for the player.
 * Manages default and premium skins, as well as the currently selected skin.
 */
public class Shop {
    private static final int SKIN_PREMIUM_PRICE = 50;
    private final Skin defaultSkin;
    private final Skin newPremiumSkin;
    private Skin selectedSkin;

    /**
     * Shop Constructor. It inizializes the default and premium skins.
     * It loads the selected skin from GameSaveManager.
     */
    public Shop() {
        this.defaultSkin = new Skin("DEFAULT_SKIN", 0, true);
        this.newPremiumSkin = new Skin("WIZARD", SKIN_PREMIUM_PRICE, GameSaveManager.getInstance().isSkinPremiumSbloccata());
        final String selected = GameSaveManager.getInstance().getSelectedSkinName();
        if ("WIZARD".equals(selected)) {
            this.selectedSkin = newPremiumSkin;
        } else {
            this.selectedSkin = defaultSkin;
        }
    }

    /**
     * @return the default skin object
     */
    public final Skin getDefaultSkin() {
        return defaultSkin;
    }

    /**
     * @return the premium skin object
     */
    public final Skin getPremiumSkin() {
        return newPremiumSkin;
    }

    /**
     * It unlocks the premium skin and updates Game Save Manager.
     */
    public final void unlockNewPremiumSkin() {
        newPremiumSkin.unlockSkin();
        GameSaveManager.getInstance().setSkinPremiumSbloccata(true);
    }

    /**
     * It selects the given skin if is unlocked and updates the Game Save Manager.
     *
     * @param skin is the skin to select
     */
    public void selectSkin(final Skin skin) {
        if (skin.isSkinUnlocked()) {
            this.selectedSkin = skin;
            GameSaveManager.getInstance().setSelectedSkinName(skin.getNameSkin());
        }
    }

    /**
     * @return the current selected skin
     */
    public Skin getSelectedSkin() {
        return selectedSkin;
    }
}
