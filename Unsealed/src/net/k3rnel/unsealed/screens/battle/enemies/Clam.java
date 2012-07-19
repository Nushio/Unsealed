package net.k3rnel.unsealed.screens.battle.enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleHero;

public class Clam extends BattleEnemy {

    public Clam(int x, int y) {
        super(30, x, y);
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/clam" );
        TextureRegion[][] spriteSheet = atlasRegion.split(41, 48);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation blocking = new Animation(2f,frames);
        blocking.setPlayMode(Animation.LOOP);
        this.animations.put("blocking",blocking);
        frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][4];
        frames[1] = spriteSheet[0][5];
        frames[2] = spriteSheet[0][6];
        frames[3] = spriteSheet[0][0];
        Animation attacking = new Animation(0.85f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        Animation idle = new Animation(0.4f,frames);
        idle.setPlayMode(Animation.NORMAL);
        this.animations.put("idle",idle);
        this.animations.put("attacking",attacking);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateBlocking);
        this.setHeight(41);
        this.setWidth(48);
    }

    public Task blocking(){
        Task task = new Task() {
            @Override
            public void run() {
                //Clam is currently inside its shell. Lets make it peek.
                Gdx.app.log(Unsealed.LOG, "Going idle...");
                setState(BattleEntity.stateIdle);
            }
        };
        return task;
    }

    public boolean action(BattleHero hero, float delta){
        boolean reschedule = false;
        stateTime+=delta;
        switch(getState()){
            case BattleEntity.stateIdle:
                if(!currentAnimation.isAnimationFinished(stateTime)){
                    if(hero.getGridY() == this.getGridY()){
                        setState(BattleEntity.stateAttacking);
                    }
                }else{
                    Gdx.app.log(Unsealed.LOG,"Setting state to blocking!");
                    setState(BattleEntity.stateBlocking);
                    reschedule = true;
                }
                break;
            case BattleEntity.stateAttacking:
                if(currentAnimation.isAnimationFinished(stateTime)){
                    if(hero.getGridY() == this.getGridY()){
                        Gdx.app.log(Unsealed.LOG,"SMACK!");
                        if(hero.getState()==BattleEntity.stateBlocking){
                            hero.setHp(hero.getHp()-5);
                            Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                            hero.setHp(0);
                        }else{
                           if( hero.setHp(hero.getHp()-10)){
                               Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                               hero.setHp(0);
                           }
                        }
                    }
                    setState(BattleEntity.stateBlocking);
                    reschedule = true;
                }
                break;
        }
        return reschedule;
    }
}
