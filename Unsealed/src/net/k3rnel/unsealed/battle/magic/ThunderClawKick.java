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

public class ThunderClawKick extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public ThunderClawKick(TextureAtlas atlas, float speed, BattleEntity entity) {
        super(speed,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/lightningclaw-rotate" );
        TextureRegion[][] spriteSheet = atlasRegion.split(64,64);
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][1];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[2][1];
        frames[3] = spriteSheet[3][1];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);

        this.setHeight(64);this.setWidth(64);
        offsetX = 0;
        offsetY = 0;

        this.setGrid(entity.getGridXInt()-1,entity.getGridYInt());
    }
    
    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getGridX()<0)
            destroyMe=true;
        for(BattleHero hero : BattleGrid.heroes){
            if(hero.getGridYInt() == this.getGridYInt() && hero.getGridXInt() == this.getGridXInt()){
                Gdx.app.log(Unsealed.LOG,"SMACK!");
                if(hero.getState()==BattleEntity.stateBlocking){
                    hero.setHp(hero.getHp()-20);
                }else{
                    if( hero.setHp(hero.getHp()-40)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        hero.setHp(0);
                    }
                }
                destroyMe=true;
            }
        }
    }
}
