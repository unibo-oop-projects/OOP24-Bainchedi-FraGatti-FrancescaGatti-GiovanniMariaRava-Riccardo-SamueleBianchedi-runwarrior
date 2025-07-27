package it.unibo.runwarrior.model;

/**
 * Class that manages skins that can be used by the player.
 * A skin has a name, price and flag indicating if it is unlocked or bought.
 */
public class Skin {
    private String nameSkin;
    private int price;
    private boolean bought;
    private boolean skinUnlocked;

    /**
     * Skin Constructor.
     *
     * @param nameSkin string that represents the name of the skin
     * @param price price required to buy the skin in the shop
     * @param unlocked true if the skin is already unlocked
     */
    public Skin(final String nameSkin, final int price, final boolean unlocked) {
        this.nameSkin = nameSkin; 
        this.price = price; 
        this.bought = false; 
        this.skinUnlocked = unlocked;
    }

    /**
     * @return the name of the skin
     */
    public final String getNameSkin() {
        return nameSkin; 
    }

    /**
     * @return the price of the skin
     */
    public final int getPrice() {
        return price; 
    }

    /**
     * @return true if the skin has been bought
     */
    public final boolean isSkinBought() {
        return bought; 
    }

    /**
     * @return true if the skin has been unlocked
     */
    public final boolean isSkinUnlocked() {
        return skinUnlocked;
    }

    /**
     * It unlocks the skin, setting its unlocked to true.
     */
    public final void unlockSkin() { //metodo da chiamare per sbloccare la skin
        this.skinUnlocked = true;
    }
}
