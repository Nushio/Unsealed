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
package net.k3rnel.unsealed.story.characters;

import net.k3rnel.unsealed.story.helpers.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Pixie extends MapCharacter {

    public Pixie(TextureAtlas atlas) {
        super(atlas);
        createPlayer();
    }
    
    public void createPlayer() {
        // Get our character images to create animations.
        TextureRegion[][] tmp = getAtlas().findRegion("char-sprites/pixie_spritesheet").split(38,38);
        
        setWidth(38);
        setHeight(38);
        
        //Create our animations
        Animation animation = new Animation(1f, new TextureRegion[]{ tmp[0][4] });
        animation.setPlayMode(Animation.LOOP);
        animations.put("frozen", animation);

        animation = new Animation(0.1f, new TextureRegion[]{ tmp[0][0], tmp[0][1], tmp[0][2], tmp[0][3],tmp[0][2],tmp[0][1],tmp[0][0] });
        animation.setPlayMode(Animation.LOOP);
        animations.put("animated", animation);
    }
    
    @Override
    public void updateAnimation() {
        if(isWalking())
            setCurrentAnim("animated");
        else
            setCurrentAnim("frozen");
    }
  
   
}
