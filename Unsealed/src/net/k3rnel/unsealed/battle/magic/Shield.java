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

public class Shield extends MagicEntity {

    public Shield(TextureAtlas atlas, BattleEntity entity) {
        super(0,0,entity);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/shield-blue" );
        TextureRegion[][] spriteSheet = atlasRegion.split(128, 128);
        TextureRegion[] frames = new TextureRegion[6];
        frames[0] = spriteSheet[1][3];
        frames[1] = spriteSheet[2][0];
        frames[2] = spriteSheet[2][1];
        frames[3] = spriteSheet[2][2];
        frames[4] = spriteSheet[3][0];
        frames[5] = spriteSheet[3][1];
        Animation blocking = new Animation(0.1f,frames);
        blocking.setPlayMode(Animation.LOOP);
        this.animations.put("blocking",blocking);
        this.setState(BattleEntity.stateBlocking);
        this.setGrid(entity.getGridXInt(),entity.getGridYInt());
        this.setHeight(128);
        this.setWidth(128);
        offsetX = 50;
        offsetY = 35;
    }
    @Override
    public void act(float delta) {
        // TODO Auto-generated method stub
        super.act(delta);
    }
    
}
