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
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;

public class TreeTrunk extends MagicEntity {

    public TreeTrunk(TextureAtlas atlas, int x, int y,BattleEntity entity) {
        super(4.5f,0,entity);

        AtlasRegion atlasRegion = atlas.findRegion("battle/entities/treetrunk");
        TextureRegion[][] spriteSheet = atlasRegion.split(21,91);
        TextureRegion[] frames = new TextureRegion[6];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][0];
        frames[3] = spriteSheet[0][1];
        frames[4] = spriteSheet[0][0];
        frames[5] = spriteSheet[0][1];
        Animation animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("blocking",animation);
        frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][4];
        frames[3] = spriteSheet[0][5];
        frames[4] = spriteSheet[0][6];
        frames[5] = spriteSheet[0][7];
        frames[6] = spriteSheet[0][8];
        frames[7] = spriteSheet[0][9];
        animation = new Animation(0.1f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        this.setState(BattleEntity.stateBlocking);
        this.setHeight(91);this.setWidth(21);
        offsetX = -13;
        offsetY = 15;
        this.setGridX(x, true);
        this.setGridY(y);


    }
    @Override
    public void act(float delta){
        stateTime+=delta;
        if(this.currentAnimation == null){
            Gdx.app.log(Unsealed.LOG,"No anim!");
            return;
        }
        this.setDrawable(new Image(this.currentAnimation.getKeyFrame(this.stateTime)).getDrawable());
        if(this.getState()==BattleEntity.stateBlocking){
            if(currentAnimation.isAnimationFinished(stateTime)){
                setState(stateAttacking);
            }
        }
        if(this.getState()==BattleEntity.stateAttacking){
            if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt())!=null){
                BattleEntity hero = BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt());
                if(hero instanceof BattleHero){
                    if(hero.setHp(hero.getHp()-40)){
                    }else{
                    }
                    destroyMe=true;
                }
            }
            if(currentAnimation.isAnimationFinished(stateTime)){
                destroyMe=true;
            }

        }



    }
}


