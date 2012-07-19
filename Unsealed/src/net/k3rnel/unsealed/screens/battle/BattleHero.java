package net.k3rnel.unsealed.screens.battle;

import net.k3rnel.unsealed.Unsealed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BattleHero extends BattleEntity {

    public boolean waitingOnAnimation = false;
    public boolean isCharging = false;
  
    private int mana;


   public BattleHero(int hp) {

        AtlasRegion atlasRegion = getAtlas().findRegion("battle/lidia");
        TextureRegion[][] lidia = atlasRegion.split(112,134);
        Animation idle = new Animation(1f, lidia[0][0]);
        idle.setPlayMode(Animation.NORMAL);
        this.animations.put("idle", idle);
        Animation blocking = new Animation(1f,lidia[0][1]);
        blocking.setPlayMode(Animation.NORMAL);
        this.animations.put("blocking",blocking);
        TextureRegion[] attackFrames = new TextureRegion[8]; 
        attackFrames[0] = lidia[0][1];
        attackFrames[1] = lidia[0][2];
        attackFrames[2] = lidia[0][3];
        attackFrames[3] = lidia[0][4];
        attackFrames[4] = lidia[0][5];
        attackFrames[5] = lidia[0][6];
        attackFrames[6] = lidia[0][7];
        attackFrames[7] = lidia[0][4];
        Animation attacking = new Animation(0.1f,attackFrames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);

        attackFrames = new TextureRegion[3]; 
        attackFrames[0] = lidia[0][4];
        attackFrames[1] = lidia[0][2];
        attackFrames[2] = lidia[0][0];
        Animation release = new Animation(0.1f,attackFrames);
        release.setPlayMode(Animation.NORMAL);
        this.animations.put("released",release);
        this.setState(BattleEntity.stateIdle);
        this.setPosition(220,150);
        
        setHp(hp);
        mana = 0;
        setGridX(1);
        setGridY(1); 
        
        this.setWidth(112);
        this.setHeight(134);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        
        batch.draw(currentAnimation.getKeyFrame(stateTime), getX(), getY());
        if(this.getState() == 1&&currentAnimation.isAnimationFinished(stateTime)){
            waitingOnAnimation = true;
            if(isCharging)
                setState(BattleEntity.stateCharging);
        }
    }
    
    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }
    /**
     * @param mana the mana to set
     */
    public void setMana(int mana) {
        this.mana = mana;
        if(this.mana<0)
            this.mana = 0;
        if(this.mana > 30)
            this.mana = 30;
    }
    
}
