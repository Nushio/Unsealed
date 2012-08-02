/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
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
import net.k3rnel.unsealed.battle.magic.ThunderClawKick;

public class Shura extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;


    public Shura(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.offsetX = 100;
        this.offsetY = 20;
        setGrid(x, y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/shura" );
        TextureRegion[][] spriteSheet = atlasRegion.split(165, 93);
        TextureRegion[] frames = new TextureRegion[5];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[1][3];
        frames[4] = spriteSheet[1][4];
        Animation idle = new Animation(0.5f,frames);
        idle.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("idle",idle);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[2][0];
        frames[1] = spriteSheet[2][1];
        frames[2] = spriteSheet[2][2];
        frames[3] = spriteSheet[2][3];
        frames[4] = spriteSheet[2][4];
        frames[5] = spriteSheet[2][5];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",attacking);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[3][0];
        frames[1] = spriteSheet[3][1];
        frames[2] = spriteSheet[3][2];
        frames[3] = spriteSheet[3][3];
        frames[4] = spriteSheet[3][4];
        frames[5] = spriteSheet[3][5];
        attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",attacking);
//                x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
        this.setHeight(93);
        this.setWidth(165);
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
                                        hero.setHp(hero.getHp()-35);
                                    else
                                        hero.setHp(hero.getHp()-70);
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
    public Shura getGhost(){
        return this;
    }

    public void showDart(boolean show){
        tmpDart = new ThunderClawKick(atlas,-8.4f,this);
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
