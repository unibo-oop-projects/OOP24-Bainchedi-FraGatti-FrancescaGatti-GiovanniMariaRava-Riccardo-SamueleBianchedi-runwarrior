package it.unibo.runwarrior.model;

public class Skin {
    private String nameSkin; 
    private int price; 
    private boolean isBought; 

    public Skin(String nameSkin, int price){
        this.nameSkin = nameSkin; 
        this.price = price; 
        this.isBought = false; 
    }

    public String getNameSkin(){
        return nameSkin; 
    }

    public int getPrice(){
        return price; 
    }

    public boolean saleState(){
        return isBought; 
    }

    public void setSaleState(boolean bought){
        this.isBought = bought;
    }
}
