package it.unibo.runwarrior.model;

public class Shop {
    private static final int SKIN_PREMIUM_PRICE = 50;
    private final Skin defaultSkin;
    private final Skin newPremiumSkin;
    private Skin selectedSkin;

    public Shop() {
        this.defaultSkin = new Skin("DEFAULT SKIN", 0, true);
        this.newPremiumSkin = new Skin("WIZARD", SKIN_PREMIUM_PRICE, GameSaveManager.getInstance().isSkinPremiumSbloccata());
        final String selected = GameSaveManager.getInstance().getSelectedSkinName();
        if ("WIZARD".equals(selected)) {
            this.selectedSkin = newPremiumSkin;
        } else {
            this.selectedSkin = defaultSkin;
        }
    }

    public final Skin getDefaultSkin() {
        return defaultSkin;
    }

    public final Skin getPremiumSkin() {
        return newPremiumSkin;
    }
    
    public final void unlockNewPremiumSkin() {
        newPremiumSkin.unlockSkin();
        GameSaveManager.getInstance().setSkinPremiumSbloccata(true);
    }

    public void selectSkin(Skin skin) {
        if (skin.isSkinUnlocked()) {
            this.selectedSkin = skin;
            GameSaveManager.getInstance().setSelectedSkinName(skin.getNameSkin());
        }
    }

    public Skin getSelectedSkin() {
        return selectedSkin;
    }
}
