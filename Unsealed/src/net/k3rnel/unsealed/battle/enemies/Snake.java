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
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.PoisonDart;

public class Snake extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;


    public Snake(TextureAtlas atlas, int x, int y) {
        super(70, x, y);
        this.offsetX = 20;
        this.offsetY = 10;
        setGrid(x, y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/snake" );
        TextureRegion[][] spriteSheet = atlasRegion.split(107, 77);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation animation = new Animation(0.75f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[3];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][4];
        animation = new Animation(0.5f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][5];
        frames[1] = spriteSheet[0][6];
        frames[2] = spriteSheet[0][7];
        frames[3] = spriteSheet[0][8];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",animation);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
        this.setHeight(77);
        this.setWidth(107);
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
                        this.setX(this.getX()-1);
//                        BattleGrid.clearGrid(getGridXInt()+1,getGridYInt());
                        setGridX((this.getX()-200)/65,false);
                        BattleGrid.clearGrid(getGridXInt()+1,getGridYInt());
                        BattleGrid.moveEntity(this, getGridXInt(),getGridYInt());
                        if(this.getGridX()>=0){
                            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                                BattleEntity entity = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                                if(entity instanceof BattleHero){
                                    if(entity.getState()==BattleEntity.stateBlocking){
                                        entity.setHp(entity.getHp()-15);
                                    }else{
                                        entity.setHp(entity.getHp()-30);
                                    }
                                }
                                if(entity!=this){
                                    BattleGrid.moveEntity(this, BattleGrid.getUnusedPosition());
                                    setState(BattleEntity.stateIdle);
                                }
                            }
                        }else{
                            BattleGrid.clearGrid(getGridXInt(),getGridYInt());
                            BattleGrid.clearGrid(getGridXInt()-1,getGridYInt());
                            BattleGrid.moveEntity(this, BattleGrid.getUnusedPosition());
                            setState(BattleEntity.stateIdle);
                        }
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
        for(MagicEntity magic : darts){
            magic.draw(batch, parentAlpha);
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
                                if(BattleGrid.random.nextInt(100)>60){
                                    setState(BattleEntity.stateAttacking);
                                }else{
                                    BattleGrid.clearGrid(getGridXInt(), getGridYInt());
                                    setState(BattleEntity.stateAltAttacking);
                                }
                            }else{
                                moveCharacter(BattleGrid.random.nextInt(4));
                                setState(BattleEntity.stateIdle);
                            }
                        }
                        break;                  
                }

            }
        };
        return currentTask;
    }
   
    public void showDart(boolean show){
        tmpDart = new PoisonDart(atlas,3,-10.4f,this);
        tmpDart.setVisible(false);
        if(show)
            tmpDart.setGrid(this.getGridXInt(),this.getGridYInt());
        tmpDart.setVisible(show);

        darts.add(tmpDart);
    }
    protected void moveCharacter(int nextMove) {
        switch(nextMove){
            case 0://Up
                if(this.getGridYInt()+1<3)
                    if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt()+1)==null){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }
                break;
            case 1://Down
                if(this.getGridYInt()-1>-1)
                    if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt()-1)==null){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                break;

            case 2://Do move towards hero
                for(BattleHero hero : BattleGrid.heroes){
                    if(hero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(hero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                }
                break;
            case 3:
                for(BattleHero hero : BattleGrid.heroes){
                    if(hero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(hero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                }
                break;
        }

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
