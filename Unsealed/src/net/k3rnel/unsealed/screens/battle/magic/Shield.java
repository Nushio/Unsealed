package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class Shield extends BattleEntity {

    public Shield() {
        super();
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/shield" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64, 64);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation idle = new Animation(0.2f,frames);
        idle.setPlayMode(Animation.LOOP);
        this.animations.put("idle",idle);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][4];
        frames[3] = spriteSheet[0][5];
        frames[4] = spriteSheet[0][6];
        frames[5] = spriteSheet[0][0];
        Animation blocking = new Animation(2f,frames);
        blocking.setPlayMode(Animation.LOOP);
        this.animations.put("blocking",blocking);
        this.setState(BattleEntity.stateIdle);
    }
    
}
