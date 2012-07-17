package net.k3rnel.unsealed.screens.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class BattleEntity extends Actor {
    
    private int hp;
    public Label hpLabel;
    
    private int gridX;
    private int gridY;
    private Skin skin;
    protected TextureAtlas atlas;
    
    public BattleEntity() {
        hpLabel = new Label("",getSkin());
        hpLabel.setColor(Color.BLACK);
    }
    
    
    public TextureAtlas getAtlas() {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        }
        return atlas;
    }

    protected Skin getSkin() {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }
    
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

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
        this.hpLabel.setText(hp+"");
    }

    /**
     * @return the hpLabel
     */
    public Label getHpLabel() {
        return hpLabel;
    }

    /**
     * @param hpLabel the hpLabel to set
     */
    public void setHpLabel(Label hpLabel) {
        this.hpLabel = hpLabel;
    }
    
    
}
