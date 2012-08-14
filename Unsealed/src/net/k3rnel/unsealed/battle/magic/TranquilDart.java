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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleEntity;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.BattleHero;

public class TranquilDart extends MagicEntity {
    
    /**
     * 0 = gray. 1 = blue. 2 = red. 3 = green.
     * @param color
     */
    public TranquilDart(TextureAtlas atlas, int color, float speed, BattleEntity entity) {
        super(speed,0,entity);
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

        this.setHeight(30);this.setWidth(30);
        offsetX = (int)entity.getWidth()-120;
        offsetY = 0;
        this.addAction(color(Color.MAGENTA));
        this.setGrid(entity.getGridXInt()-1,entity.getGridYInt());
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
                    
                }else{
                    if( tmpHero.setHp(tmpHero.getHp()-15)){
                        Gdx.app.log(Unsealed.LOG, "The clams have avenged themselves! You died a miserable death");
                        tmpHero.setHp(0);
                    }
                    tmpHero.setStatus(BattleEntity.statusStunned);
                }
                destroyMe=true;
            }
        }
    }
}
