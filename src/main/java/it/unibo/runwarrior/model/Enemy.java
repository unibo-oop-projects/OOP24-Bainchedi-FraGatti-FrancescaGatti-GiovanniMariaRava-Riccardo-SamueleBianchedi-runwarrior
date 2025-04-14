package it.unibo.runwarrior.model;

public interface Enemy {
    public void render(Graphics g);

    public void die();
    
    public void update();

}
