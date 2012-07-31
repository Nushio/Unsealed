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

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.k3rnel.unsealed.battle.BattleEntity;

public class MagicEntity extends BattleEntity {
    
    public float speedX;
    public float speedY;
    public boolean isArc = false;
    public float initialPos = 0;
    public float maxDistance = 1;
    BattleEntity entity;
    public boolean destroyMe = false;
    
    public MagicEntity(float speedX, float speedY, BattleEntity entity) {
        super(); 
        this.speedX = speedX;
        this.speedY = speedY;
        this.entity = entity;
        setPosition(entity.getX(),entity.getY());
        setGridX(entity.getGridXInt(),false);
        setGridY(entity.getGridYInt());
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        this.setX(this.getX()+speedX);
//        this.setGridX((this.getX()+1)*65+150 - this.getWidth() - offsetX);
        setGridX((this.getX()-200)/65,false);
        // Lastplacer's law of uncertainty. 
        // p*sin(pi*x/q)
        // Where p is max height. q is max distance. x is where I'm standing. And I'm sitting down.
        if(isArc)
            this.setY((float)(initialPos+((100*Math.sin(Math.PI*getGridX()/maxDistance)))));
        
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        
    }
}
