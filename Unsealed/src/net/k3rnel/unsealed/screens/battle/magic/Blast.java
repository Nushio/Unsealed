package net.k3rnel.unsealed.screens.battle.magic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class Blast extends BattleEntity {

    public Blast() {
        super();
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/fireball" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64, 64);
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][4];
        frames[3] = spriteSheet[0][5];
        Animation attacking = new Animation(2f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);
    }
}
