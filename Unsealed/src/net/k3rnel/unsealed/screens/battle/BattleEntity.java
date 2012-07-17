package net.k3rnel.unsealed.screens.battle;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BattleEntity extends Actor {
    
    private int gridX;
    private int gridY;
    
    /**
     * @return the gridX
     */
    public int getGridX() {
        return gridX;
    }
    /**
     * @param gridX the gridX to set
     */
    public void setGridX(int gridX) {
        this.gridX = gridX;
    }
    /**
     * @return the gridY
     */
    public int getGridY() {
        return gridY;
    }
    /**
     * @param gridY the gridY to set
     */
    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
}
