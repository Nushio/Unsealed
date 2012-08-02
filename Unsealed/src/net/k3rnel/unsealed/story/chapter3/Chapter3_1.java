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
package net.k3rnel.unsealed.story.chapter3;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Lidia;

public class Chapter3_1 extends AbstractChapter {

  
    /**
     * Chapter Three: Walled Garden
     * @param game
     */
    public Chapter3_1(Unsealed game) {
        super(game);
        mapname="Terrex Mountain";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.MOUNTAIN );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1400,310);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        
        camera.zoom = 1.0f;
        camera.update();       
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        stage.getSpriteBatch().begin();
        //This is probably the bestest "Scene Director" ever made. 
        //Valve should totally hire me. 
        for(MapCharacter character : characters){
            if(character instanceof Lidia){
                switch(act){
                    case 0:
                        character.setPosition(1400,310);
                        centerCamera(character);
                        character.getColor().a = 0;
                        character.setWalking(false);
                        actions = sequence(fadeIn(0.95f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 1:
                        dialog.setText("Lidia: Malus Grove is just past these mountains.\n" +
                        		"I need to stay alert, who knows what sorts of creatures live in this area");
                        dialog.setVisible(true);
                        break;
                    case 2:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 3;
                        break;
                    case 3:
                        character.setWalking(true);
                        if(character.getY()<631){
                            character.setY(character.getY()+1);
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 4;
                        }
                       break;
                    case 4:
                        if(character.getX()>1390){
                            character.setY(character.getY()+1);
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            act = 5;
                        }
                        break;
                    case 5:
                        game.setScreen(new Chapter3_2(game));
                        break;
                }
            }
            character.act(delta);
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
        
        if(dialog.isVisible()){
            hud.act(delta);
            hud.draw();
        }
        
    }   

 
}
