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
import net.k3rnel.unsealed.story.characters.Shura;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter2_1 extends AbstractChapter {

  
    /**
     * Chapter Two: Old Friends
     * @param game
     */
    public Chapter2_1(Unsealed game) {
        super(game);
        mapname="RouteTwo";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.DESERT );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(0,1600);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirRight);
        characters.add(tmpChar);
        
        tmpChar = new Shura(getAtlas());
        tmpChar.setPosition(1860,1960);
        tmpChar.updateAnimation();
        tmpChar.setDirection(MapCharacter.dirDown);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        camera.zoom = 2.5f;
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
                        if(character.getX()<801){
                            character.setX(character.getX()+2);
                            centerCamera(character);
                        }else{
                            camera.zoom = 2.0f;
                            camera.update();
                            
                            act = 2;
//                        }
                        }
                       break;
                    case 2:
                        if(character.getX()<1201){
                            character.setX(character.getX()+2);
                            centerCamera(character);
                        }else{
                            camera.zoom = 1.8f;
                            camera.update();
                            act = 3;
//                        }
                        }
                        break;
                    case 3:
                        if(character.getX()<1401){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            act = 4;
//                        }
                        }
                        break;
                    case 14:
                        if(character.getX()<2201){
                            character.setWalking(true);
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            act = 15;
//                        }
                        }
                        break;
                    case 15:
                        game.setScreen(new Chapter2_2(game));
                        break;
                   
                }
            }
            if(character instanceof Shura){
                switch(act){
                    case 4:
                        character.setVisible(true);
                        if(character.getY()>1800){
                            character.setWalking(true);
                            character.setY(character.getY()-1);
                        }else{
                            camera.zoom = 1.6f;
                            camera.update();
                            act = 5;
                        }
                        break;
                    case 5:
                        if(character.getY()>1600){
                            character.setY(character.getY()-1);
                        }else{
                            camera.zoom = 1.3f;
                            camera.update();
                            act = 6;
                            character.setDirection(MapCharacter.dirLeft);
                        }
                        break;
                    case 6:
                        if(character.getX()>1600){
                            character.setX(character.getX()-1);
                        }else{
                            camera.zoom = 1.0f;
                            camera.update();
                            act = 7;
                            character.setWalking(false);
                        }
                        break;
                    case 7:
                        dialog.setText("Lidia: Shura! It's been a while since I last saw you. \n" +
                                "Did you change your mind and decide to help me unseal the Pixies?");
                        dialog.setVisible(true);
                        break;
                    case 8:
                        dialog.setText("Shura: Help you unseal Pixies? Hahahaha. No.");
                        break;
                    case 9:
                        dialog.setText("Lidia: What were you doing in that cave then?");
                        break;
                    case 10:
                        dialog.setText("Shura: It's really none of your business, but if you must know, I was hunting Terrex\n" +
                        		"They are mostly wanted for their horns here, but imagine if I took them back home!");
                        break;
                    case 11://TODO Drop a mention about pixies and magic
                        dialog.setText("Shura: I couldn't find any in the caves, apparently they fled towards the mountain.\n " +
                        		"I'll catch you later, gotta go!");
                        break;
                    case 12:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirRight);
                        character.setWalking(true);
                        act = 13;
                        break;
                    case 13:
                        if(character.getX()<1800){
                            character.setX(character.getX()+2.5f);
                        }else{
                          act = 14;
                          character.setVisible(false);
                        }
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
