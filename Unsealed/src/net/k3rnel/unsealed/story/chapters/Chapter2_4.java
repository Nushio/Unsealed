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
package net.k3rnel.unsealed.story.chapters;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter2_4 extends AbstractChapter {


    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_4(Unsealed game) {
        super(game);
        mapname="RouteOne";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.GRASS  );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1300,1400);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        
       
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
                        character.setWalking(true);
                        break;
                    case 1:
                        if(character.getY()<1570){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 2;
                        }
                        break;
                    case 2:
                        if(character.getX()>1020){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            act = 3;
                        }
                        break;
                    case 3:
                        game.setScreen(new Chapter2_5(game));
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
