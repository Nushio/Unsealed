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

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;

public class Turtle extends BattleEnemy {

    public Turtle(TextureAtlas atlas, int x, int y) {
        super(30, x, y);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/turtle" );
        TextureRegion[][] spriteSheet = atlasRegion.split(41, 48);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation moving = new Animation(2f,frames);
        moving.setPlayMode(Animation.LOOP);
        this.animations.put("moving",moving);
        frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][4];
        frames[1] = spriteSheet[0][5];
        frames[2] = spriteSheet[0][6];
        frames[3] = spriteSheet[0][0];
        Animation attacking = new Animation(0.85f,frames);
        attacking.setPlayMode(Animation.NORMAL);
        frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][2];
        frames[3] = spriteSheet[0][3];
        Animation idle = new Animation(0.4f,frames);
        idle.setPlayMode(Animation.NORMAL);
        this.animations.put("idle",idle);
        this.animations.put("attacking",attacking);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateMoving);
        this.setHeight(41);
        this.setWidth(48);
    }

    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateIdle:
                        //Turtle is being stupid. Lets make it either attack or move.
                        break;
                    case BattleEntity.stateAttacking:
                        //Turtle is attacking. Lets make it hurt something.
                        break;
                    case BattleEntity.stateMoving:
                        //Turtle is moving. Lets move your body gurrl.
                        break;
                }
               
            }
        };
        return currentTask;
    }

    @Override
    public void act(float delta) {
        super.act(delta);   
        switch(getState()){
            case BattleEntity.stateIdle:
                break;
            case BattleEntity.stateAttacking:
                break;
            case BattleEntity.stateMoving:
                break;
        }
    }
  
}
