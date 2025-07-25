package it.unibo.runwarrior.model;

import java.util.List;

public class Shop {
    private final Skin newPremiumSkin;

    public Shop(List<Skin> skins){
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
