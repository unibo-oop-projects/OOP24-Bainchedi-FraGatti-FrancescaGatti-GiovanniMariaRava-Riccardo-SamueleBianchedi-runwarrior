package it.unibo.runwarrior.model;


public class Shop {
    private final Skin newPremiumSkin;

    public Shop(){
        this.newPremiumSkin = new Skin("WIZARD", 50, GameSaveManager.getInstance().isSkinPremiumSbloccata()); 
    }

    public Skin getPremiumSkin(){
        return newPremiumSkin;
    }
    public void unlockNewPremiumSkin(){
        newPremiumSkin.unlockSkin();
        GameSaveManager.getInstance().setSkinPremiumSbloccata(true);
    }
}
