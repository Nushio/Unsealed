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
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.PeaDart;

public class Clam extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;
    public Clam(TextureAtlas atlas, int hp, int x, int y) {
        super(hp, x, y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/clam" );
        TextureRegion[][] spriteSheet = atlasRegion.split(41, 48);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation animation = new Animation(1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("blocking",animation);
        frames = new TextureRegion[4];
        frames[0] = spriteSheet[0][2];
        frames[1] = spriteSheet[0][3];
        frames[2] = spriteSheet[0][3];
        frames[3] = spriteSheet[0][3];
        animation = new Animation(0.85f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("idle",animation);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[0][4];
        frames[1] = spriteSheet[0][5];
        frames[2] = spriteSheet[0][6];
        frames[3] = spriteSheet[0][5];
        frames[4] = spriteSheet[0][3];
        frames[5] = spriteSheet[0][2];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateBlocking);
        this.setHeight(48);
        this.setWidth(48);
    }

    BattleHero tmpHero;
    @Override
    public void act(float delta) {
        super.act(delta);
        for(int i = 0; i< darts.size(); i++){
            if(darts.get(i).destroyMe){
                darts.remove(i);
                i--;
            }else{
                darts.get(i).act(delta);
            }
        }  
        if(this.getStatus()!=BattleEntity.statusStunned)
        switch(getState()){
            case BattleEntity.stateIdle:
                if(!currentAnimation.isAnimationFinished(stateTime)){
                    for(int i = 0;i<BattleGrid.heroes.size;i++){
                        tmpHero = BattleGrid.heroes.get(i);
                        if(tmpHero.getGridYInt() == this.getGridYInt()){
                            setState(BattleEntity.stateAttacking);
                            hit = false;
                        }
                    }
                }else{
                    Gdx.app.log(Unsealed.LOG,"Setting state to blocking!");
                    setState(BattleEntity.stateBlocking);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(4));
                }
                break;
            case BattleEntity.stateAttacking:
                if(currentAnimation.isAnimationFinished(stateTime+0.3f)&&!hit){
                        hit = true;
                        showDart(true);
                }
                if(currentAnimation.isAnimationFinished(stateTime)){
                    setState(BattleEntity.stateBlocking);
                    BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(4));
                }
                break;
        }
        
    }
    
    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(int i = 0; i< darts.size(); i++){
           darts.get(i).draw(batch,parentAlpha);
        }
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
    
    public void showDart(boolean show){
        tmpDart = new PeaDart(atlas,2,-8f,this);
        tmpDart.setVisible(false);
        if(show)
            tmpDart.setVisible(show);

        darts.add(tmpDart);
    }
}
