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

public class Bee extends BattleEnemy {

    TextureAtlas atlas;

    public Bee(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.offsetX = 25;
        this.offsetY = 20;
        setGrid(x, y);
        this.atlas = atlas;
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/bee" );
        TextureRegion[][] spriteSheet = atlasRegion.split(82, 78);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][4];
        frames[3] = spriteSheet[0][5];
        frames[4] = spriteSheet[0][6];
        frames[5] = spriteSheet[0][7];
        animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",animation);
      
        this.setState(BattleEntity.stateIdle);
        this.setHeight(78);
        this.setWidth(82);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getStatus()!=BattleEntity.statusStunned)
            switch(getState()){
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
        

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
       
    }
    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateIdle:
                        for(BattleHero hero : BattleGrid.heroes){
                            if(BattleGrid.random.nextInt(100)>70){
                                if(BattleGrid.checkGrid(hero.getGridXInt()+1,hero.getGridYInt())==null){
                                    if(BattleGrid.moveEntity(getBee(),hero.getGridXInt()+1,hero.getGridYInt()))
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
    public Bee getBee(){
        return this;
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
