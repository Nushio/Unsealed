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

import net.k3rnel.unsealed.story.MapCharacter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class FireLionMap extends MapCharacter {

    public FireLionMap(TextureAtlas atlas) {
        super(atlas);
        createPlayer();
    }
    
    public void createPlayer() {
        // Get our character images to create animations.
        TextureRegion[][] tmp = getAtlas().findRegion("battle/entities/firelion").split(64, 64);
        
        setWidth(64);
        setHeight(64);
        
        //Create our animations
        Animation animation = new Animation(1f, new TextureRegion[]{ tmp[0][0] });
        animation.setPlayMode(Animation.LOOP);
        animations.put("micro", animation);

        animation = new Animation(0.1f, new TextureRegion[]{ tmp[0][1]});
        animation.setPlayMode(Animation.LOOP);
        animations.put("mini", animation);
        
        animation = new Animation(0.1f, new TextureRegion[]{ tmp[0][2]});
        animation.setPlayMode(Animation.LOOP);
        animations.put("normal", animation);
        
        animation = new Animation(0.1f, new TextureRegion[]{
                tmp[0][0],tmp[0][1],tmp[0][2],tmp[0][3],
                tmp[1][0],tmp[1][1],tmp[1][2],tmp[1][3],
                tmp[2][0],tmp[2][1],tmp[2][2],tmp[2][3],
                tmp[3][0],tmp[3][1],tmp[3][2],tmp[3][3],
                });
        animation.setPlayMode(Animation.LOOP);
        animations.put("full", animation);
    }
    
    @Override
    public void updateAnimation() {
        switch(getDirection()){
            case 1:
                setCurrentAnim("micro");
                break;
            case 2:
                setCurrentAnim("mini");
                break;
            case 3:
                setCurrentAnim("normal");
                break;
            case 4:
                setCurrentAnim("full");
                break;
        }
    }
  
   
}
