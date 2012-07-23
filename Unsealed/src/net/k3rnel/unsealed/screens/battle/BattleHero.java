package net.k3rnel.unsealed.screens.battle;

import net.k3rnel.unsealed.screens.battle.magic.Blast;
import net.k3rnel.unsealed.screens.battle.magic.Cannonball;
import net.k3rnel.unsealed.screens.battle.magic.FireLion;
import net.k3rnel.unsealed.screens.battle.magic.Shield;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer.Task;

public class BattleHero extends BattleEntity {

    public boolean isCharging = false;
    public boolean isShooting = false;

    private int mana;
    
    private FireLion lion;
    private Cannonball ball;
    private Blast blast;
    private Shield shield;

    private TextureAtlas atlas;
    
    public BattleHero(TextureAtlas atlas, int hp) {
        this.atlas = atlas;
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
        frames = new TextureRegion[5]; 
        frames[0] = lidia[2][0];
        frames[1] = lidia[2][1];
        frames[2] = lidia[2][2];
        frames[3] = lidia[2][3];
        frames[4] = lidia[2][4];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);

        frames = new TextureRegion[3]; 
        frames[0] = lidia[2][5];
        frames[1] = lidia[2][6];
        frames[2] = lidia[2][7];
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
        setGridX(1);
        setGridY(1); 

        this.setWidth(65);
        this.setHeight(93);
        getBlast();
        getLion();
        getCannonball();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(blast.isVisible())
            blast.act(delta);
        if(ball.isVisible())
            ball.act(delta);
        if(this.getState() == BattleEntity.stateAttacking){
            this.isShooting = true;
        }else if(this.getState() == BattleEntity.stateBlocking){
            if(shield.isVisible())
                getShield().setGrid(this.getGridX(),this.getGridY());
        }
        if(this.getState() != BattleEntity.stateBlocking){
            showShield(false);
        }
        if(currentAnimation.isAnimationFinished(stateTime)){
            switch(this.getState()){
                case BattleEntity.stateAttacking:
                    setState(BattleEntity.stateAltAttacking);
                    break;
                case BattleEntity.stateAltAttacking:
                    setState(BattleEntity.stateIdle);
                    break;
                case BattleEntity.stateBlocking:
                    break;
            }
        }
    }
    public FireLion getLion(){
        if(lion==null){
            lion = new FireLion(atlas,0,1,this);
            lion.setVisible(false);
        }
        return lion;
    }
    public void showLion(boolean show){
        lion.offsetY = -30;
        lion.offsetX = -(int)this.getWidth();
        lion.speedX = 0.01f;
        lion.maxDistance = 2;
        if(show)
            lion.setGrid(this.getGridX(),this.getGridY());
        lion.setVisible(show);
    }
    public Blast getBlast(){
        if(blast==null){
            blast = new Blast(atlas,0,1,this);
            blast.setVisible(false);
        }
        return blast;
    }
    public void showBlast(boolean show){
        if(blast==null)
            getBlast();
        blast.offsetY = -30;
        blast.offsetX = -(int)this.getWidth();
        blast.speedX = 0.5f;
        if(show)
            blast.setGrid(this.getGridX(),this.getGridY());
        blast.setVisible(show);
    }
    public Cannonball getCannonball(){
        if(ball==null){
            ball = new Cannonball(atlas,0,1,this);
            ball.setVisible(false);
        }
        return ball;
    }
    public void showCannonball(boolean show){
        if(ball==null)
            getCannonball();
        ball.offsetY = -30;
        ball.offsetX = -(int)this.getWidth();
        ball.speedX = 0.01f;
        ball.isArc = true;
        ball.initialPos = this.getY();
        ball.maxDistance = this.getGridX()+2;
        if(show)
            ball.setGrid(this.getGridX(),this.getGridY());
        ball.setVisible(show);
    }
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
        shield.setVisible(show);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(blast.isVisible())
            blast.draw(batch, parentAlpha);
        if(ball.isVisible())
            ball.draw(batch, parentAlpha);
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
