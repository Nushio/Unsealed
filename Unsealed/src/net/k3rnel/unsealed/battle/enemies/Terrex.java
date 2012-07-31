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
import com.badlogic.gdx.utils.Timer.Task;

import net.k3rnel.unsealed.battle.BattleEnemy;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;
import net.k3rnel.unsealed.battle.magic.PeaDart;

public class Terrex extends BattleEnemy {

    List<PeaDart> darts;
    PeaDart tmpDart;
    TextureAtlas atlas;
    public Terrex(TextureAtlas atlas, int x, int y) {
        super(100, x, y);
        this.offsetX = 32;
        this.offsetY = 10;
        setGrid(x, y);
        this.atlas = atlas;
        darts = new ArrayList<PeaDart>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/terrex" );
        TextureRegion[][] spriteSheet = atlasRegion.split(94, 77);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation animation = new Animation(0.5f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[8];
        frames[0] = spriteSheet[2][0];
        frames[1] = spriteSheet[2][1];
        frames[2] = spriteSheet[2][2];
        frames[3] = spriteSheet[2][3];
        frames[4] = spriteSheet[2][4];
        frames[5] = spriteSheet[2][5];
        frames[6] = spriteSheet[2][6];
        frames[7] = spriteSheet[2][7];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        frames = new TextureRegion[7];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[1][3];
        frames[4] = spriteSheet[1][4];
        frames[5] = spriteSheet[1][5];
        frames[6] = spriteSheet[1][6];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("altattacking",animation);
        //        x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
        this.setHeight(77);
        this.setWidth(94);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(this.getStatus()!=BattleEntity.statusStunned)
            switch(getState()){
                case BattleEntity.stateAttacking:
                    if(currentAnimation.isAnimationFinished(stateTime)){
                        showDart(true);
                        setState(BattleEntity.stateIdle);
                    }
                    break;
                case BattleEntity.stateAltAttacking:
                    if(currentAnimation.isAnimationFinished(stateTime)){
                        for(BattleHero hero : BattleGrid.heroes){
                            if(hero.getGridYInt() == getGridYInt()){
                                if(hero.getGridXInt() == getGridXInt()-1){
                                    if(hero.getState()==BattleEntity.stateBlocking) 
                                        hero.setHp(hero.getHp()-10);
                                    else
                                        hero.setHp(hero.getHp()-20);
                                    BattleGrid.moveEntity(hero,hero.getGridXInt()-1,hero.getGridYInt());
                                    hero.setState(BattleEntity.stateIdle);
                                }
                            }
                        }
                        setState(BattleEntity.stateIdle);
                    }
                    break;
            }
        for(int i = 0; i< darts.size(); i++){
            if(darts.get(i).destroyMe){
                darts.remove(i);
                i--;
            }else{
                darts.get(i).act(delta);
            }
        }  
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for(PeaDart dart : darts){
            dart.draw(batch, parentAlpha);
        }
    }
    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateIdle:
                        for(BattleHero hero : BattleGrid.heroes){
                            if(hero.getGridYInt() == getGridYInt()){
                                if(hero.getGridXInt() == getGridXInt()-1)
                                    setState(BattleEntity.stateAltAttacking);
                                else
                                    setState(BattleEntity.stateAttacking);
                            }
                        }
                        if(getState() != BattleEntity.stateAttacking && getState() != BattleEntity.stateAltAttacking){
                            moveCharacter(BattleGrid.random.nextInt(7));
                            setState(BattleEntity.stateIdle);
                        }
                        break;                  
                }

            }
        };
        return currentTask;
    }

    protected void moveCharacter(int nextMove) {
        switch(nextMove){
            case 0://Up
                if(this.getGridYInt()+1<3)
                    if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt()+1)==null){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }
                break;
            case 1://Down
                if(this.getGridYInt()-1>-1)
                    if(BattleGrid.checkGrid(this.getGridXInt(),this.getGridYInt()-1)==null){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                break;
            case 2://Left
                if(this.getGridXInt()-1>2)
                    if(BattleGrid.checkGrid(this.getGridXInt()-1,this.getGridYInt())==null){
                        BattleGrid.moveEntity(this, this.getGridXInt()-1, this.getGridYInt());
                    }
                break;
            case 3://Right
                if(this.getGridXInt()+1<6)
                    if(BattleGrid.checkGrid(this.getGridXInt()+1,this.getGridYInt())==null){
                        BattleGrid.moveEntity(this, this.getGridXInt()+1, this.getGridYInt());
                    }
                break;
            case 4://Do move towards hero
                for(BattleHero hero : BattleGrid.heroes){
                    if(hero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(hero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                }
                break;
            case 5:
                for(BattleHero hero : BattleGrid.heroes){
                    if(hero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(hero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                }
                break;
            case 6:
                for(BattleHero hero : BattleGrid.heroes){
                    if(hero.getGridYInt() == getGridYInt() ){
                        if(getGridXInt()>2)
                            BattleGrid.moveEntity(this, this.getGridXInt()-1, this.getGridYInt());
                    }
                }
                break;
        }

    }
    @Override
    public void setState(int state) {
        super.setState(state);
        switch(state){
            case BattleEntity.stateIdle:
                BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(3)+1);
                break;
        }
    }
    public void showDart(boolean show){
        tmpDart = new PeaDart(atlas,3,-12.5f,this);
        tmpDart.setVisible(false);
        if(show)
            tmpDart.setGrid(this.getGridXInt(),this.getGridYInt());
        tmpDart.setVisible(show);

        darts.add(tmpDart);
    }
}
