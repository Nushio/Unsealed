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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Shura;
import net.k3rnel.unsealed.story.characters.Whisperer;

public class Chapter3_5 extends AbstractChapter {

    final int[] overLayers = { 5 };
    /**
     * Chapter Three: Walled Garden
     * @param game
     */
    public Chapter3_5(Unsealed game) {
        super(game);
        mapname="RouteThree";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.MOUNTAIN );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1260,0);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        
        tmpChar = new Shura(getAtlas());
        tmpChar.setPosition(1950,1000);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirLeft);
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

//                character.setPosition(1260,0);
//                centerCamera(character);
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
                        character.setWalking(true);
                        if(character.getY()<600){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            act = 2;
                        }
                       break;
                    case 2:
                        character.setWalking(true);
                        if(character.getY()<1000){
                            character.setY(character.getY()+1);
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            act = 3;
                        }
                       break;
                    case 3:
                        if(character.getX()<1900){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            act = 4;
                        }
                       break;
                    case 4:
                        dialog.setText("Shura: Lidia... Xios is up ahead. Do you really think you can defeat him?");
                        dialog.setVisible(true);
                        break;
                    case 5:
                        dialog.setText("Lidia: What option do I have? Allow him to continue, unchallenged?\n" +
                        		"No. He has to be stopped. No one should restrict others' Freedom!");
                        break;
                    case 6:
                        dialog.setText("Shura: If you really believe that, then let's see if you can defeat me!");
                        break;
                    case 7:
                        dialog.setText("Lidia: What? Now is not the time to be playing!");
                        break;
                    case 8:
                        dialog.setText("Shura: If not now, when?");
                        break;
                    case 9:
                        dialog.setVisible(false);
                        game.setScreen(new Chapter3_6(game));
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
