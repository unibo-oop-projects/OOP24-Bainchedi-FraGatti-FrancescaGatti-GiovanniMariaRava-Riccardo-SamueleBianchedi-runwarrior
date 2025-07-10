package it.unibo.runwarrior.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.runwarrior.model.Score;
import it.unibo.runwarrior.model.Shop;
import it.unibo.runwarrior.model.Skin;

public class ShopController {
    private final Score score;
    private final Shop shop;
    
    public ShopController(Score score){
        this.score = score; 
        List<Skin> skins = new ArrayList<>();
        skins.add(new Skin("Warrior", 0)); 
        skins.add(new Skin("Wizard", 30000));
        this.shop = new Shop(skins);
    }

    public void buySkin(Skin skin){
        if(!skin.saleState() && score.spendCoins(skin.getPrice())){
            skin.setSaleState(true); //campio lo stato di vendita in true così so che la skin è stata venduta
        } else{
            System.err.println("monete insufficienti o skin già comprata");
        }
    }

    public Shop getShop(){
        return this.shop;
    }

    public int getCoinScore(){
        return score.getCoinScore();
    }

    public List<Skin> getAllSkins(){
        return shop.getAllSkins();
    }

}
