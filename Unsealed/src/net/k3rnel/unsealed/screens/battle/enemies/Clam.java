package net.k3rnel.unsealed.screens.battle.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleEntity;

public class Clam extends BattleEnemy {

    public Clam(int hp, int x, int y) {
        super(hp, x, y);
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/clam" );
        TextureRegion[][] spriteSheet = atlasRegion.split(41, 48);
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
        Animation attacking = new Animation(2f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);
//        x = (int)((battleoverlay.getWidth()/2)/6);
//        y = (int)((battleoverlay.getHeight())/3);
//        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
    }

}
