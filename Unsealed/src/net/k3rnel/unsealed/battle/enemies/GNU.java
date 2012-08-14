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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;

public class GNU extends BattleEnemy {

    TextureAtlas atlas;
    public GNU(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.atlas = atlas;
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/gnu" );
        TextureRegion[][] spriteSheet = atlasRegion.split(97,90);
        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = spriteSheet[0][0];
        Animation animation = new Animation(1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("blocking",animation);
      
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[0][1];
        frames[1] = spriteSheet[0][2];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
      
        this.setState(BattleEntity.stateBlocking);
        this.setHeight(97);
        this.setWidth(90);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        
        
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
                    case BattleEntity.stateBlocking:
                        //Clam is currently inside its shell. Lets make it peek.
                        Gdx.app.log(Unsealed.LOG, "Going idle...");
                        setState(BattleEntity.stateIdle);
                        break;
                }

            }
        };
        return currentTask;
    }
    
}
