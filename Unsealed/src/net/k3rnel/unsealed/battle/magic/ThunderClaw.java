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
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;

public class ThunderClaw extends MagicEntity {

    public ThunderClaw(TextureAtlas atlas, BattleEntity entity) {
        super(4.5f,0,entity);

        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/lightningclaw");
        TextureRegion[][] spriteSheet = atlasRegion.split(64,64);
        TextureRegion[] frames = new TextureRegion[16];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[1][0];
        frames[5] = spriteSheet[1][1];
        frames[6] = spriteSheet[1][2];
        frames[7] = spriteSheet[1][3];
        frames[8] = spriteSheet[2][0];
        frames[9] = spriteSheet[2][1];
        frames[10] = spriteSheet[2][2];
        frames[11] = spriteSheet[2][3];
        frames[12] = spriteSheet[3][0];
        frames[13] = spriteSheet[3][1];
        frames[14] = spriteSheet[3][2];
        frames[15] = spriteSheet[3][3];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateAttacking);
        this.setHeight(64);this.setWidth(64);
        offsetX = 15;
        offsetY = 15;
        this.setGridY(entity.getGridY());
        this.setGridX(entity.getGridX()+3,true);

    }
    @Override
    public void act(float delta){
        stateTime+=delta;
        if(this.currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        this.setDrawable(new Image(this.currentAnimation.getKeyFrame(this.stateTime)).getDrawable());
        if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
            BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
            if(enemy instanceof BattleEnemy){
                enemy.setStatus(BattleEntity.statusStunned);
            }
        }

        if(this.getState()==BattleEntity.stateAttacking){
            if(currentAnimation.isAnimationFinished(stateTime)){
                 if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                    BattleEntity enemy = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                    if(enemy instanceof BattleEnemy){
                        if(enemy.setHp(enemy.getHp()-40)){
                        }else{
                            enemy.setStatus(BattleEntity.statusNormal);
                        }
                    }
                }
                destroyMe=true;
            }
        }



    }
}


