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

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.characters.FireLionMap;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_2 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_2(Unsealed game) {
        super(game);
        mapname="TownOne";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.TOWN );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1924,1608);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),0);
        tmpChar.setPosition(1040,970);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),1);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.setPosition(1100,900);
        characters.add(tmpChar);
        
        tmpChar = new Kid(getAtlas(),2);
        tmpChar.setPosition(1000,930);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        
        tmpChar = new FireLionMap(getAtlas());
        tmpChar.setPosition(1080,950);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.updateAnimation();
        tmpChar.setVisible(false);
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
                        character.setDirection(MapCharacter.dirLeft);
                        character.getColor().a = 0;
                        character.setWalking(false);
                        
                        actions = sequence(fadeIn(0.75f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 1:
                        dialog.setText("Lidia: So this is New Lion? I wonder if magic has returned here already.");
                        dialog.setVisible(true);
                        break;
                    case 2:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        if(character.getX()>1050){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            dialog.setText("Lidia: Hmm, I wonder what that noise was. I better check it out.");
                            dialog.setVisible(true);
                        }
                        break;
                    case 3:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        character.setDirection(MapCharacter.dirRight);
                        setAct(4);
                        break;
                    case 4:
                        if(character.getX()<1140){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirDown);
                            setAct(5);
                        }
                        break;
                    case 5:
                        if(character.getY()>1000){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            setAct(6);
                        }
                    case 6:
                        if(character.getY()==1000){
                            dialog.setText("Mimi: Watch this! Watch this!");
                            dialog.setVisible(true);
                        }
                        break;
                }
                
            }
            if(character instanceof Kid){
                if(((Kid)character).kid==0){
                    switch(act){
                        case 7:
                            dialog.setVisible(false);
                            actions = sequence(color(Color.RED),delay(0.3f),color(Color.WHITE));
                            character.addAction(actions);
                            setAct(8);
                            break;
                    }
                }
                if(((Kid)character).kid==1){
                   
                }
                if(((Kid)character).kid==2){
                }
            }
            if(character instanceof FireLionMap){
                switch(act){
                    case 8:
                        character.setVisible(true);
                        if(character.getX()<1130){
                            character.setX(character.getX()+2);
                        }else{
                            character.setVisible(false);
                            setAct(9);
                        }
                        break;
                    case 9:
                        dialog.setText("Leon: Wow! That's amazing!");
                        dialog.setVisible(true);
                        break;
                    case 10:
                        dialog.setText("Maria: Do it again! Do it again!");
                        break;
                    case 11:
                        dialog.setText("Lidia: Wait!\n" +
                        		"Magic can be dangerous. If you don't know what you're doing, you might hurt someone!");
                        break;
                    case 12:
                        dialog.setText("Mimi: Look at those clothes! She's not from around here!");
                        
                        break;
                    case 13:
                        dialog.setText("Leon: Who are you and where did you come from?");
                        break;
                    case 14:
                        dialog.setText("Lidia: You can call me Lidia, and you're right. I'm a Traveler.\n" +
                        		"We travel through the lands, guiding those that want to hear us");
                        break;
                    case 15:
                        dialog.setText("Maria: Can you teach us how to use magic?\n" +
                        		"Mimi: Yeah, can you? Pwease?");
                        break;
                    case 16:
                        dialog.setText("Lidia: Sure. Stand back everyone.");
                        break;
                    case 17:
                        dialog.setVisible(false);
                        setAct(18);
                        break;
                    case 18:
                        game.setScreen( new Chapter1_3( game ) );
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
