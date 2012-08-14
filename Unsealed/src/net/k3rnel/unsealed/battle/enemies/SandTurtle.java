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
import net.k3rnel.unsealed.battle.magic.MagicEntity;
import net.k3rnel.unsealed.battle.magic.PeaDart;

public class SandTurtle extends BattleEnemy {

    List<MagicEntity> darts;
    MagicEntity tmpDart;
    TextureAtlas atlas;


    public SandTurtle(TextureAtlas atlas, int hp, int x, int y) {
        super(hp,x,y);
        this.atlas = atlas;
        darts = new ArrayList<MagicEntity>();
        AtlasRegion atlasRegion = atlas.findRegion( "battle/entities/turtle" );
        TextureRegion[][] spriteSheet = atlasRegion.split(80, 80);
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = spriteSheet[0][0];
        frames[1] = spriteSheet[0][1];
        Animation animation = new Animation(1f,frames);
        animation.setPlayMode(Animation.LOOP);
        this.animations.put("idle",animation);
        frames = new TextureRegion[6];
        frames[0] = spriteSheet[1][0];
        frames[1] = spriteSheet[1][1];
        frames[2] = spriteSheet[1][2];
        frames[3] = spriteSheet[1][3];
        frames[4] = spriteSheet[1][4];
        frames[5] = spriteSheet[1][5];
        animation = new Animation(0.2f,frames);
        animation.setPlayMode(Animation.NORMAL);
        this.animations.put("attacking",animation);
        offsetX = 20;
        offsetY = 10;
        setGrid(x, y);
//                x = (int)((battleoverlay.getWidth()/2)/6);
        //        y = (int)((battleoverlay.getHeight())/3);
        //        this.setPosition(x*this.getGridX(),y*this.getGridY());
        this.setState(BattleEntity.stateIdle);
        this.setHeight(80);
        this.setWidth(80);
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
        for(int i = 0; i< darts.size(); i++){
            darts.get(i).draw(batch, parentAlpha);
        }  
    }
    BattleHero tmpHero;
    @Override
    public Task nextTask(){
        currentTask = new Task() {
            @Override
            public void run() {
                switch(getState()){
                    case BattleEntity.stateIdle:
                        for(int i = 0;i<BattleGrid.heroes.size;i++){
                            tmpHero = BattleGrid.heroes.get(i);
                            if(tmpHero.getGridYInt() == getGridYInt()){
                                setState(BattleEntity.stateAttacking);
                            }else{
                                moveCharacter();
                                setState(BattleEntity.stateIdle);
                            }
                        }
                        break;                  
                }

            }
        };
        return currentTask;
    }
    public SandTurtle getGhost(){
        return this;
    }

    public void showDart(boolean show){
        tmpDart = new PeaDart(atlas,4,-10.4f,this);
        tmpDart.setVisible(false);
        if(show)
            tmpDart.setGrid(this.getGridXInt(),this.getGridYInt());
        tmpDart.setVisible(show);

        darts.add(tmpDart);
    }
    protected void moveCharacter() {
        switch(BattleGrid.random.nextInt(4)){
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

            case 2://Do move towards hero
                for(int i = 0;i<BattleGrid.heroes.size;i++){
                    tmpHero = BattleGrid.heroes.get(i);
                    if(tmpHero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(tmpHero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
                    }
                }
                break;
            case 3:
                for(int i = 0;i<BattleGrid.heroes.size;i++){
                    tmpHero = BattleGrid.heroes.get(i);
                    if(tmpHero.getGridYInt() > getGridYInt() && getGridYInt()<3){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()+1);
                    }else if(tmpHero.getGridYInt() < getGridYInt() && getGridYInt()>=0){
                        BattleGrid.moveEntity(this, this.getGridXInt(), this.getGridYInt()-1);
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
                BattleGrid.timer.scheduleTask(nextTask(),BattleGrid.random.nextInt(5));
                break;
        }
    }
}
