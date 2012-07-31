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

import net.k3rnel.unsealed.battle.BattleEntity;

public class Cannonball extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public Cannonball(TextureAtlas atlas, int color, int speed, BattleEntity entity) {
        super(.1f,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/fireball" );
        TextureRegion[][] spriteSheet = atlasRegion.split(34, 25);
        TextureRegion[] frames = new TextureRegion[3];
        frames[0] = spriteSheet[color][0];
        frames[1] = spriteSheet[color][1];
        frames[2] = spriteSheet[color][2];
        Animation attacking = new Animation(0.1f,frames);
        attacking.setPlayMode(Animation.LOOP);
        this.animations.put("attacking",attacking);
        this.setState(BattleEntity.stateAttacking);
        this.setGrid(entity.getGridXInt()+1,entity.getGridYInt());

        this.setHeight(30);this.setWidth(30);
        offsetX = -(int)entity.getWidth();
        offsetY = 30;
    }
}
