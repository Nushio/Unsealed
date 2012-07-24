package net.k3rnel.unsealed.screens.battle.enemies;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleGrid;
import net.k3rnel.unsealed.screens.battle.BattleHero;
import net.k3rnel.unsealed.screens.battle.magic.PeaDart;

public class Snake extends BattleEnemy {

    PeaDart dart;
    TextureAtlas atlas;
    public Snake(TextureAtlas atlas, int x, int y) {
        super(30, x, y);
        this.atlas = atlas;
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/snake" );
        TextureRegion[][] spriteSheet = atlasRegion.split(107, 77);
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
        frames = new TextureRegion[5];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][3];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[0][3];
        Animation idle = new Animation(0.4f,frames);
        idle.setPlayMode(Animation.NORMAL);
        this.animations.put("idle",idle);
        this.animations.put("attacking",attacking);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateBlocking);
        this.setHeight(77);
        this.setWidth(107);
        getDart();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(dart!=null)
            if(dart.isVisible())
                dart.act(delta);
        switch(getState()){
            case BattleEntity.stateIdle:
                if(!currentAnimation.isAnimationFinished(stateTime)){
                    for(BattleHero hero : BattleGrid.heroes){
                        if(hero.getGridYInt() == this.getGridYInt()){
                            setState(BattleEntity.stateAttacking);
                        }
                    }
                }else{
                    Gdx.app.log(Unsealed.LOG,"Setting state to blocking!");
                    setState(BattleEntity.stateBlocking);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(5));
                }
                break;
            case BattleEntity.stateAttacking:
                if(currentAnimation.isAnimationFinished(stateTime)){
                    showDart(true);
                    
                    setState(BattleEntity.stateBlocking);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(5));
                }
                break;
        }
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(dart!=null&&dart.isVisible())
            dart.draw(batch, parentAlpha);
    }
    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateBlocking:
                        //Clam is currently inside its shell. Lets make it peek.
                        Gdx.app.log(Unsealed.LOG, "Going idle...");
                        setState(BattleEntity.stateIdle);
                        break;
                }

            }
        };
        return currentTask;
    }

    
    
    public PeaDart getDart(){
        if(dart==null){
            dart = new PeaDart(atlas,-0.4f,this);
            dart.setVisible(false);
        }
        return dart;
    }
    public void showDart(boolean show){
        dart.offsetY = 0;
        dart.offsetX = (int)this.getWidth();
        if(show)
            dart.setGrid(this.getGridXInt(),this.getGridYInt());
        dart.setVisible(show);
    }
}
