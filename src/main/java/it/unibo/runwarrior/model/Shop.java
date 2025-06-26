package it.unibo.runwarrior.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Skin> skinList; 

    public Shop(List<Skin> skins){
        this.skinList = new ArrayList<>(skins);
    }

    public void addSkin(Skin skin){
        skinList.add(skin);
    }

    public List<Skin> getAllSkins(){
        return skinList;
    }
    
    void resetShop(){
        for(Skin skin : skinList){
            skin.setSaleState(false);
        }
    }
}
