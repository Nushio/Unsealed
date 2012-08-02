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

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.magic.MagicEntity;
/**
 * Only a cat with a different cloak a Lion still has claws... 
 * @author Nushio
 *
 */
public class FakeXios extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;
    public FakeXios(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/xios" );
        TextureRegion[][] spriteSheet = atlasRegion.split(304, 294);
        TextureRegion[] frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[1][0];
        frames[3] = spriteSheet[1][1];
        this.canBeMoved = false;
        Animation animation = new Animation(0.3f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[8];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        frames[2] = spriteSheet[1][0];
        frames[3] = spriteSheet[1][1];
        frames[4] = spriteSheet[0][0];
        frames[5] = spriteSheet[0][1];
        frames[6] = spriteSheet[1][0];
        frames[7] = spriteSheet[1][1];
        animation = new Animation(0.3f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        this.animations.put("altattacking",animation);
        this.setState(BattleEntity.stateIdle);
        this.setWidth(304);
        this.setHeight(294);
        this.setX(320);
        this.setY(100);
        this.hpLabel.setX(470);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        //        setGrid(4,1);
        //        this.setX(320);
        //        this.setY(100);
        this.setVisible(true);
        
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(MagicEntity dart : darts){
            dart.draw(batch, parentAlpha);
        }
    }
}