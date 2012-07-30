package net.k3rnel.unsealed.battle.enemies;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.magic.PeaDart;

public class Dummy extends BattleEnemy {

    List<PeaDart> darts;
    PeaDart tmpDart;
    TextureAtlas atlas;


    public Dummy(TextureAtlas atlas, int x, int y) {
        super(40, x, y);
        this.offsetX = 10;
        setGrid(x, y);
        this.atlas = atlas;
        darts = new ArrayList<PeaDart>();
        AtlasRegion atlasRegion = atlas.findRegion( "char-sprites/combat_dummy" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64, 64);
        TextureRegion[] frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[0][4];
        frames[5] = spriteSheet[0][5];
        frames[6] = spriteSheet[0][6];
        frames[7] = spriteSheet[0][7];
        Animation animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[1][3];
        frames[4] = spriteSheet[1][4];
        frames[5] = spriteSheet[1][5];
        animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("blocking",animation);
        this.setState(BattleEntity.stateIdle);
        this.setHeight(64);
        this.setWidth(64);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.setDrawable(new Image(this.currentAnimation.getKeyFrame(this.stateTime)).getDrawable());

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }
    @Override
    public boolean setHp(int hp) {
        super.setHp(hp);
        if(getHp()>0){
            return false;
        }else{
            setState(BattleEntity.stateBlocking);
            return true;
        }
    }

    @Override
    public void setState(int state) {
        super.setState(state);
        switch(state){
            case BattleEntity.stateAttacking:
                actions = sequence(fadeOut(0.75f));
                this.addAction(actions);
                break;
        }
    }
}
