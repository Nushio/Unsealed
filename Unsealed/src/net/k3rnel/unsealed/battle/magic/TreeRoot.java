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
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;

public class TreeRoot extends MagicEntity {
    
    public TreeRoot(TextureAtlas atlas, int y, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/treeroot" );
        TextureRegion[][] spriteSheet = atlasRegion.split(88, 88);
        TextureRegion[] frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        frames[4] = spriteSheet[0][4];
        frames[5] = spriteSheet[0][5];
        frames[6] = spriteSheet[0][6];
        frames[7] = spriteSheet[0][7];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);

        this.setHeight(88);this.setWidth(88);
        offsetX = (int)entity.getWidth()-320;
        offsetY = 30;

        this.setGrid(entity.getGridXInt()-1,y);
    }
    BattleHero tmpHero;
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getGridX()<0)
            destroyMe=true;
        for(int i = 0; i< BattleGrid.heroes.size;i++){
            tmpHero = BattleGrid.heroes.get(i);
            if(tmpHero.getGridYInt() == this.getGridYInt() && tmpHero.getGridXInt() == this.getGridXInt()){
                if(tmpHero.getState()==BattleEntity.stateBlocking){
                    tmpHero.setHp(tmpHero.getHp()-10);
                }else{
                    if( tmpHero.setHp(tmpHero.getHp()-20)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        tmpHero.setHp(0);
                    }else{
                        tmpHero.setStatus(BattleEntity.statusPoisoned);
                    }
                }
                destroyMe=true;
            }
        }
    }
}
