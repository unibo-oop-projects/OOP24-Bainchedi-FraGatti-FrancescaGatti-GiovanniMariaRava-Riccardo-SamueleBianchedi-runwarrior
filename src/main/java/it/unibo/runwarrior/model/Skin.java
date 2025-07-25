package it.unibo.runwarrior.model;

public class Skin {
    private String nameSkin; 
    private int price; 
    private boolean bought; 
    private boolean skinUnlocked; 

    public Skin(final String nameSkin, final int price, final boolean unlocked) {
        this.nameSkin = nameSkin; 
        this.price = price; 
        this.bought = false; 
        this.skinUnlocked = unlocked;
    }

    public final String getNameSkin() {
        return nameSkin; 
    }
 
    public final int getPrice() {
        return price; 
    }

    public final boolean isSkinBought() {
        return bought; 
    }

    public final boolean isSkinUnlocked() {
        return skinUnlocked;
    }

    public final void unlockSkin() { //metodo da chiamare per sbloccare la skin
        this.skinUnlocked = true;
    }
}
