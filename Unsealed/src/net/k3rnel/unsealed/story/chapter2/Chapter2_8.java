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
package net.k3rnel.unsealed.story.chapter2;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Lidia;

public class Chapter2_8 extends AbstractChapter {

    final int[] overLayers = { 12 };
    Image dojo;
    
    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_8(Unsealed game) {
        super(game);
        mapname="Asia Town";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.DOJO );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1100,100);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        
        //Backup plan due to libgdx not rendering friggin' flipped tiles
        dojo = new Image(getAtlas().findRegion("maps/dojo to render"));
        dojo.setX(540);
        dojo.setY(1344);
       
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);

      
        stage.getSpriteBatch().begin();
        dojo.draw(stage.getSpriteBatch(), 1);
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
                        break;
                    case 1:
                        dialog.setText("Lidia: I finally made it to Ryuken. ");
                        dialog.setVisible(true);
                       break;
                    case 2:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 3;
                        break;
                    case 3:
                        if(character.getY()<800){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            character.setWalking(false);
                            act = 4;
                        }
                        break;
                    case 4:
                        dialog.setText("Lidia: Where is everyone? It looks deserted!");
                        dialog.setVisible(true);
                        break;
                    case 5:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirUp);
                        character.setWalking(true);
                        act = 6;
                        break;
                    case 6:
                        if(character.getY()<1000){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            act = 7;
//                            character.setY(1400);
//                            centerCamera(character);
//                            camera.zoom = 1f;
//                            camera.update();
//                            character.setDirection(MapCharacter.dirUp);
//                            character.setWalking(false);
//                            act = 6;
//                            centerCamera(character);
//                            character.setX(character.getX()-1);
                        }
                        break;
                    case 7:
                        game.setScreen(new Chapter2_9(game));
                        break;
                        
                   
                }
            }
           
            character.act(delta);
            if(character.isVisible())
                character.draw(stage.getSpriteBatch(), 1);
        }
        stage.getSpriteBatch().end();
        tileMapRenderer.render(camera,overLayers);
        if(dialog.isVisible()){
            hud.act(delta);
            hud.draw();
        }
        
    }   

 
}
