package it.unibo.runwarrior.model;

public class Skin {
    private final String nameSkin; 
    private final int price; 
    private boolean bought; 
    private boolean skinUnlocked; 

    public Skin(String nameSkin, int price, boolean unlocked){
        this.nameSkin = nameSkin; 
        this.price = price; 
        this.bought = false; 
        this.skinUnlocked = unlocked;
    }

    public String getNameSkin(){
        return nameSkin; 
    }

    public int getPrice(){
        return price; 
    }

    public boolean isSkinBought(){
        return bought; 
    }

    public boolean isSkinUnlocked(){
        return skinUnlocked;
    }
    public void unlockSkin(){ //metodo da chiamare per sbloccare la skin
        this.skinUnlocked = true;
    }
}
