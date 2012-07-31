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

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;

public class Spikes extends MagicEntity {

    public Spikes(TextureAtlas atlas,int type, BattleEntity entity) {
        super(4.5f,0,entity);

        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/spikes");
        TextureRegion[][] spriteSheet = atlasRegion.split(64,64);
        TextureRegion[] frames = new TextureRegion[9];
        frames[0] = spriteSheet[type][0];
        frames[1] = spriteSheet[type][1];
        frames[2] = spriteSheet[type][2];
        frames[3] = spriteSheet[type][3];
        frames[4] = spriteSheet[type][4];
        frames[5] = spriteSheet[type][5];
        frames[6] = spriteSheet[type][6];
        frames[7] = spriteSheet[type][7];
        frames[8] = spriteSheet[type][8];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.LOOP_PINGPONG);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateAttacking);
        this.setHeight(64);this.setWidth(64);
        offsetX = -(int)entity.getWidth()/2;
        offsetY = 10;
        this.setGridY(entity.getGridY());
        this.setX(entity.getX()+offsetX);

    }
    @Override
    public void act(float delta){
        super.act(delta);
        if(this.getX() > 550){
            destroyMe=true;
        }else{
            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                if(enemy instanceof BattleEnemy){
                    enemy.setStatus(BattleEntity.statusStunned);
                    if(enemy.getState() == BattleEntity.stateBlocking){
                        if(enemy.setHp(enemy.getHp()-5)){

                        }else{
                            enemy.setStatus(BattleEntity.statusStunned);
                            enemy.setState(BattleEntity.stateIdle);
                        }
                    }else{
                        if(enemy.setHp(enemy.getHp()-3)){

                        }else{
                            enemy.setStatus(BattleEntity.statusStunned);
                            enemy.setState(BattleEntity.stateIdle);
                        }
                    }
                }
            }
        }
    }
}


