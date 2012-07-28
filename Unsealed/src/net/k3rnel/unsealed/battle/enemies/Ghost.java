package net.k3rnel.unsealed.battle.enemies;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;
import net.k3rnel.unsealed.battle.magic.PeaDart;

public class Ghost extends BattleEnemy {

    List<PeaDart> darts;
    PeaDart tmpDart;
    TextureAtlas atlas;


    public Ghost(TextureAtlas atlas, int x, int y) {
        super(70, x, y);
        this.offsetX = 55;
        setGrid(x, y);
        this.atlas = atlas;
        darts = new ArrayList<PeaDart>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/ghost" );
        TextureRegion[][] spriteSheet = atlasRegion.split(102, 90);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        Animation idle = new Animation(0.5f,frames);
        idle.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("idle",idle);
        frames = new TextureRegion[11];
        frames[0] = spriteSheet[0][3];
        frames[1] = spriteSheet[0][4];
        frames[2] = spriteSheet[0][5];
        frames[3] = spriteSheet[0][6];
        frames[4] = spriteSheet[0][7];
        frames[5] = spriteSheet[0][8];
        frames[6] = spriteSheet[0][9];
        frames[7] = spriteSheet[0][8];
        frames[8] = spriteSheet[0][9];
        frames[9] = spriteSheet[0][8];
        frames[10] = spriteSheet[0][9];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);
        attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",attacking);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
        this.setHeight(90);
        this.setWidth(102);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getStatus()!=BattleEntity.statusStunned)
            switch(getState()){
                case BattleEntity.stateAttacking:
                    if(currentAnimation.isAnimationFinished(stateTime)){
                        showDart(true);
                        setState(BattleEntity.stateIdle);
                    }
                    break;
                case BattleEntity.stateAltAttacking:
                    if(currentAnimation.isAnimationFinished(stateTime)){
                        for(BattleHero hero : BattleGrid.heroes){
                            if(hero.getGridYInt() == getGridYInt()){
                                if(hero.getGridXInt() == getGridXInt()-1){
                                    if(hero.getState()==BattleEntity.stateBlocking) 
                                        hero.setHp(hero.getHp()-10);
                                    else
                                        hero.setHp(hero.getHp()-20);
                                    hero.setState(BattleEntity.stateIdle);
                                }
                            }
                        }
                        moveCharacter();
                        setState(BattleEntity.stateIdle);
                    }
                    break;
            }
        for(int i = 0; i< darts.size(); i++){
            if(darts.get(i).destroyMe){
                darts.remove(i);
                i--;
            }else{
                darts.get(i).act(delta);
            }
        }  

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(PeaDart dart : darts){
            dart.draw(batch, parentAlpha);
        }
    }
    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateIdle:
                        for(BattleHero hero : BattleGrid.heroes){
                            if(hero.getGridYInt() == getGridYInt()){
                                setState(BattleEntity.stateAttacking);
                            }else if(BattleGrid.random.nextInt(100)>70){
                                if(BattleGrid.checkGrid(hero.getGridXInt()+1,hero.getGridYInt())==null){
                                    if(BattleGrid.moveEntity(getGhost(),hero.getGridXInt()+1,hero.getGridYInt()))
                                        setState(BattleEntity.stateAltAttacking);
                                }
                            }else{
                                moveCharacter();
                                setState(BattleEntity.stateIdle);
                            }
                        }
                        break;                  
                }

            }
        };
        return currentTask;
    }
    public Ghost getGhost(){
        return this;
    }

    public void showDart(boolean show){
        tmpDart = new PeaDart(atlas,3,-10.4f,this);
        tmpDart.setVisible(false);
        if(show)
            tmpDart.setGrid(this.getGridXInt(),this.getGridYInt());
        tmpDart.setVisible(show);

        darts.add(tmpDart);
    }
    protected void moveCharacter() {
        BattleGrid.moveEntity(this,BattleGrid.getUnusedPosition());
    }
    @Override
    public void setState(int state) {
        super.setState(state);
        switch(state){
            case BattleEntity.stateIdle:
                BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(5));
                break;
        }
    }
}
