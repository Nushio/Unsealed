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
package net.k3rnel.unsealed.battle.skills;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.k3rnel.unsealed.battle.BattleEntity;


public class FirePunch extends AbstractSkill {

     public FirePunch(TextureAtlas atlas) {
        super(atlas);
        id = 1;
        manaCost=5;
        this.setY(10);
        stance = BattleEntity.stateAltAttacking;
        spells =  atlasRegion.split(64,64);
        this.setDrawable(new TextureRegionDrawable(spells[2][0]));
    }
   
}
