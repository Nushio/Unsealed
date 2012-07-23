package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class FireLion extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public FireLion(TextureAtlas atlas, int color, int speed, BattleEntity entity) {
        super(.1f,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/firelion_left" );
        TextureRegion[][] spriteSheet = atlasRegion.split(128, 128);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[color][0];
        frames[1] = spriteSheet[color][1];
        frames[2] = spriteSheet[color][2];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);
        this.setGrid(entity.getGridX()+1,entity.getGridY());

        this.setHeight(128);this.setWidth(128);
        offsetX = -(int)entity.getWidth();
        offsetY = 30;
    }
}
