package net.k3rnel.unsealed.battle;


import java.util.ArrayList;
import java.util.List;

import net.k3rnel.unsealed.battle.magic.Blast;
import net.k3rnel.unsealed.battle.magic.FireLion;
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.Shield;
import net.k3rnel.unsealed.battle.magic.Spikes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer.Task;

public class BattleHero extends BattleEntity {

    private int mana;

    private Shield shield;

    public List<MagicEntity> magics;
    private MagicEntity tmpMagic;
    public int magicType;
    
    private TextureAtlas atlas;

    
    private boolean hit = false;

    public BattleHero(TextureAtlas atlas, int hp) {
        this.atlas = atlas;
        magics = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/lidia");
        TextureRegion[][] lidia = atlasRegion.split(65,93);
        TextureRegion[] frames = new TextureRegion[5]; 
        frames[0] = lidia[0][0];
        frames[1] = lidia[0][1];
        frames[2] = lidia[0][2];
        frames[3] = lidia[0][3];
        frames[4] = lidia[0][4];
        Animation idle = new Animation(0.5f, frames);
        idle.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("idle", idle);
        Animation blocking = new Animation(1f,lidia[1][0]);
        blocking.setPlayMode(Animation.NORMAL);
        this.animations.put("blocking",blocking);
        frames = new TextureRegion[8]; 
        frames[0] = lidia[2][0];
        frames[1] = lidia[2][1];
        frames[2] = lidia[2][2];
        frames[3] = lidia[2][3];
        frames[4] = lidia[2][4];
        frames[5] = lidia[2][5];
        frames[6] = lidia[2][6];
        frames[7] = lidia[2][7];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);

        frames = new TextureRegion[7]; 
        frames[0] = lidia[3][0];
        frames[1] = lidia[3][1];
        frames[2] = lidia[3][2];
        frames[3] = lidia[3][3];
        frames[4] = lidia[3][4];
        frames[5] = lidia[3][5];
        frames[6] = lidia[3][6];
        attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",attacking);
        frames = new TextureRegion[3]; 
        frames[0] = lidia[0][4];
        frames[1] = lidia[0][2];
        frames[2] = lidia[0][0];
        Animation release = new Animation(0.1f,frames);
        release.setPlayMode(Animation.NORMAL);
        this.animations.put("released",release);
        this.setState(BattleEntity.stateIdle);
        this.setPosition(220,150);

        setHp(hp);
        mana = 0;
        setGridX(1,false);
        setGridY(1); 

        this.setWidth(65);
        this.setHeight(93);
        getShield();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        
        if(currentAnimation.isAnimationFinished(stateTime)){
            switch(this.getState()){
                case BattleEntity.stateAttacking:
                    hit = false;
                    setState(BattleEntity.stateIdle);
                    break;
                case BattleEntity.stateAltAttacking:
                    hit = false;
                    setState(BattleEntity.stateIdle);
                    break;
                case BattleEntity.stateBlocking:
                    break;
            }
        }
        if(this.getState() == BattleEntity.stateAttacking){
            if(currentAnimation.animationDuration<stateTime+0.4f&&!hit){
                hit = true;
                switch(magicType){
                    case 0:
                        showBlast(true);
                        break;
                    case 1:
                        showFireLion(true);
                        break;
                    case 2:
                        showGroundSpikes(true);                        
                        break;
                }
            }
        }else if(this.getState() == BattleEntity.stateBlocking){
            shield.act(delta);
                getShield().setGrid(this.getGridXInt(),this.getGridYInt());
        }else if(this.getState() == BattleEntity.stateAltAttacking){
            if(currentAnimation.animationDuration<stateTime+0.3f&&!hit){
                hit = true;
                if(BattleGrid.checkGrid(this.getGridXInt()+1,this.getGridYInt())!=null){
                    BattleEntity entity = BattleGrid.checkGrid(this.getGridXInt()+1,this.getGridYInt());
                    if(entity instanceof BattleEnemy){
                        if(entity.getState() == BattleEntity.stateBlocking){
                            if(entity.setHp(entity.getHp()-30)){
                                BattleGrid.enemies.removeValue((BattleEnemy)entity,false);
                                BattleGrid.clearGrid(entity.getGridXInt(),entity.getGridYInt());
                                BattleGrid.checkState();
                            }else{
                               entity.setStatus(BattleEntity.statusBurned);
                            }
                        }else{
                            if(entity.setHp(entity.getHp()-60)){
                                BattleGrid.enemies.removeValue((BattleEnemy)entity,false);
                                BattleGrid.clearGrid(entity.getGridXInt(),entity.getGridYInt());
                                BattleGrid.checkState();
                            }else{
                                entity.setStatus(BattleEntity.statusBurned);
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i< magics.size(); i++){
            if(magics.get(i).destroyMe){
                magics.remove(i);
                i--;
            }else{
                magics.get(i).act(delta);
            }
        }            
        if(this.getState() != BattleEntity.stateBlocking){
            showShield(false);
        }
    }
    public void reset(){
        magics = new ArrayList<MagicEntity>();
    }
    public void showFireLion(boolean show){
        tmpMagic = new FireLion(atlas,4f,this);
        tmpMagic.setVisible(false);
        tmpMagic.offsetY = 0;
        tmpMagic.offsetX = 0;
        tmpMagic.maxDistance = 5;
        if(show){  
            tmpMagic.setGrid(this.getGridXInt()+1,this.getGridYInt());
        }
        tmpMagic.setVisible(show);
        magics.add(tmpMagic);
     }
    public void showBlast(boolean show){
       tmpMagic = new Blast(atlas,0,6.5f,this);
       tmpMagic.setVisible(false);
       tmpMagic.offsetY = -30;
       tmpMagic.offsetX = -(int)this.getWidth();
       if(show)
           tmpMagic.setGrid(this.getGridXInt(),this.getGridYInt());
       tmpMagic.setVisible(show);
       magics.add(tmpMagic);
    }

    public void showGroundSpikes(boolean show){
       tmpMagic = new Spikes(atlas,1,this);
       tmpMagic.setVisible(false);
       if(show)
           tmpMagic.setGrid(this.getGridXInt(),this.getGridYInt());
       tmpMagic.setVisible(show);
       magics.add(tmpMagic);
    }
    //    public Cannonball getCannonball(){
    //        if(ball==null){
    //            ball = new Cannonball(atlas,0,1,this);
    //            ball.setVisible(false);
    //        }
    //        return ball;
    //    }
    //    public void showCannonball(boolean show){
    //        if(ball==null)
    //            getCannonball();
    //        ball.offsetY = -30;
    //        ball.offsetX = -(int)this.getWidth();
    //        ball.speedX = 0.01f;
    //        ball.isArc = true;
    //        ball.initialPos = this.getY();
    //        ball.maxDistance = this.getGridX()+2;
    //        if(show)
    //            ball.setGrid(this.getGridX(),this.getGridY());
    //        ball.setVisible(show);
    //    }
    public Shield getShield(){
        if(shield==null){
            shield = new Shield(atlas,this);
            shield.setVisible(false);
        }
        return shield;
    }
    public void showShield(boolean show){
        //        if(show)
        //            shield.setGrid(this.getGridX(),this.getGridY());
//        shield.setVisible(show);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(MagicEntity magic : magics){
            magic.draw(batch, parentAlpha);
        }
        if(this.getState()==BattleEntity.stateBlocking)
            shield.draw(batch, parentAlpha);
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
    @Override
    public Task nextTask() {
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateBlocking:
                        if(getMana()>=1){
                            setMana(getMana()-1);
                        }else{
                            setMana(0);
                            setState(BattleEntity.stateIdle);
                        }
                        break;
                    default:
                        if(getMana()<30)
                            setMana(getMana()+1);
                        break;
                }

            }
        };
        return currentTask;
    }
}
