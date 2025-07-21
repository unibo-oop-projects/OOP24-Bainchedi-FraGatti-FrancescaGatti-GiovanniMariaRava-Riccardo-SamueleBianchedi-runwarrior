package it.unibo.runwarrior.view.enemy.api;
/**
 * 
 */
public interface EnemyViewFactory {
    public void register(int type, EnemyView view) ;
    public EnemyView get(int type);
        
}
