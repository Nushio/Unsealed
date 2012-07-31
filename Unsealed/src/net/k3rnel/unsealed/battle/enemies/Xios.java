package net.k3rnel.unsealed.battle.enemies;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.PeaDart;
import net.k3rnel.unsealed.battle.magic.TreeRoot;
import net.k3rnel.unsealed.battle.magic.TreeTrunk;

public class Xios extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;
    public Xios(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/xios" );
        TextureRegion[][] spriteSheet = atlasRegion.split(304, 294);
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[1][0];
        frames[3] = spriteSheet[1][1];
        this.canBeMoved = false;
        Animation animation = new Animation(0.3f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[1][0];
        frames[3] = spriteSheet[1][1];
        frames[4] = spriteSheet[0][0];
        frames[5] = spriteSheet[0][1];
        frames[6] = spriteSheet[1][0];
        frames[7] = spriteSheet[1][1];
        animation = new Animation(0.3f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        this.animations.put("altattacking",animation);
        this.setState(BattleEntity.stateIdle);
        this.setWidth(304);
        this.setHeight(294);
        this.setX(320);
        this.setY(100);
        this.hpLabel.setX(470);
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
//        setGrid(4,1);
//        this.setX(320);
//        this.setY(100);
        this.setVisible(true);
        for(int i = 0; i< darts.size(); i++){
            if(darts.get(i).destroyMe){
                darts.remove(i);
                i--;
            }else{
                darts.get(i).act(delta);
            }
        }  
        if(this.getStatus()!=BattleEntity.statusStunned)
        switch(getState()){
            case BattleEntity.stateIdle:
                if(!currentAnimation.isAnimationFinished(stateTime)){
                    for(BattleHero hero : BattleGrid.heroes){
                        if(hero.getGridYInt() == this.getGridYInt()){
                            setState(BattleEntity.stateAttacking);
                            hit = false;
                        }
                    }
                }else{
                    Gdx.app.log(Unsealed.LOG,"Setting state to idle!");
                    setState(BattleEntity.stateIdle);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(4));
                }
                break;
            case BattleEntity.stateAttacking:
                if(currentAnimation.isAnimationFinished(stateTime+0.3f)&&!hit){
                        hit = true;
                        showRoot(true);
                }
                if(currentAnimation.isAnimationFinished(stateTime)){
                    setState(BattleEntity.stateIdle);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(4));
                }
                break;
            case BattleEntity.stateAltAttacking:
                if(currentAnimation.isAnimationFinished(stateTime+0.3f)&&!hit){
                        hit = true;
                        showTrunk(BattleGrid.random.nextInt(3),BattleGrid.random.nextInt(3),true);
                        showTrunk(BattleGrid.random.nextInt(3),BattleGrid.random.nextInt(3),true);
                        showTrunk(BattleGrid.random.nextInt(3),BattleGrid.random.nextInt(3),true);
//                        showTrunk(1,1,true);
                }
                if(currentAnimation.isAnimationFinished(stateTime)){
                    setState(BattleEntity.stateIdle);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(4));
                }
                break;
        }
        
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(MagicEntity dart : darts){
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
                                hit = false;
                                setState(BattleEntity.stateAttacking);
                            }else if(BattleGrid.random.nextInt(100)>30){
                                hit = false;
                                setState(BattleEntity.stateAltAttacking);
                            }else{
                                setState(BattleEntity.stateIdle);
                            }
                        }
                        break; 
                }

            }
        };
        return currentTask;
    }
    
    public void showRoot(boolean show){
        tmpDart = new TreeRoot(atlas,-9f,this);
        tmpDart.setVisible(show);
        darts.add(tmpDart);
    }
    public void showTrunk(int x, int y, boolean show){
        tmpDart = new TreeTrunk(atlas,x,y,this);
        tmpDart.setVisible(show);
        darts.add(tmpDart);
    }
    @Override
    public void setState(int state) {
        super.setState(state);
        switch(state){
            case BattleEntity.stateIdle:
                BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(5));
                break;
            case BattleEntity.stateAttacking:
                actions = sequence(color(Color.YELLOW), delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW),delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW), delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.YELLOW),delay(0.2f),
                        color(Color.ORANGE),delay(0.1f),
                        color(Color.WHITE),delay(0.1f));
                this.addAction(actions);
                break;
            case BattleEntity.stateAltAttacking:
                actions = sequence(color(Color.BLUE), delay(0.2f),
                        color(Color.GREEN),delay(0.1f),
                        color(Color.BLUE),delay(0.2f),
                        color(Color.GREEN),delay(0.1f),
                        color(Color.BLUE), delay(0.2f),
                        color(Color.GREEN),delay(0.1f),
                        color(Color.BLUE),delay(0.2f),
                        color(Color.GREEN),delay(0.1f),
                        color(Color.WHITE),delay(0.1f));
                this.addAction(actions);
                break;
        }
    }
}