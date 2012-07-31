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
package net.k3rnel.unsealed.battle.magic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;

public class FireLion extends MagicEntity {

    public FireLion(TextureAtlas atlas, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/firelion" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64, 64);
        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = spriteSheet[0][2];
        Animation animation = new Animation(1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[5];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[2][2];
        frames[4] = spriteSheet[2][3];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",animation);

        frames = new TextureRegion[4];
        frames[0] = spriteSheet[3][0];
        frames[1] = spriteSheet[3][1];
        frames[2] = spriteSheet[3][2];
        frames[3] = spriteSheet[3][3];
        animation = new Animation(0.5f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",animation);
        this.setState(BattleEntity.stateIdle);
        this.setGridY(entity.getGridY());
        offsetX = (int)entity.getWidth()/2;
        this.setX(entity.getX()+offsetX);
        this.setHeight(64);this.setWidth(64);
       
        offsetY = -30;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getX() > 550){
            destroyMe=true;
        }else{
            if(this.getState()==BattleEntity.stateIdle){
                if(this.getGridXInt()<5){
                    if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                        if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt()) instanceof BattleEnemy){
                            this.speedX = 2.5f;
                            this.setGridX(getGridX(),false);
                            this.setState(stateAttacking);
                        }
                    }else if(BattleGrid.checkGrid(this.getGridXInt()+1,this.getGridYInt())!=null){
                        if(BattleGrid.checkGrid(this.getGridXInt()+1,this.getGridYInt()) instanceof BattleEnemy){
                            this.speedX = 2.5f;
                            this.setGridX(getGridX()+1,false);
                            this.setState(stateAttacking);
                        }
                    }
                }else{
                    destroyMe=true;  
                }
            }else if(this.getState()==stateAttacking){
                if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                    BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                    if(enemy instanceof BattleEnemy){
                        if(enemy.getState() == BattleEntity.stateBlocking){
                            enemy.setStatus(BattleEntity.statusBurned);
                            Gdx.app.log(Unsealed.LOG, "You burned me!");
                            maxDistance = 0;
                            setState(BattleEntity.stateAttacking);
                        }else{
                            if(enemy.setHp(enemy.getHp()-(int)maxDistance*10)){
                                maxDistance--;
                                setState(BattleEntity.stateAttacking);
                            }else{
                                enemy.setStatus(BattleEntity.statusBurned);
                                maxDistance--;
                                setState(BattleEntity.stateAttacking);
                            }
                        }
                        maxDistance--;
                    }
                }
            }
            if(maxDistance<=0){
                destroyMe=true;
            }
        }
    }
}
