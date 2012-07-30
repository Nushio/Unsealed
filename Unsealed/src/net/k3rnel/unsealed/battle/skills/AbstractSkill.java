package net.k3rnel.unsealed.battle.skills;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class AbstractSkill extends Image {
    
    public int id;
    public int manaCost;
    public int stance;
    public int offsetX;
    public int offsetY;
    AtlasRegion atlasRegion;
    
    public AbstractSkill(TextureAtlas atlas){
        atlasRegion = atlas.findRegion("battle/ui/spells");
        setHeight(64); setWidth(64);
    }
    
}
