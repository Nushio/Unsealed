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
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.ChapterSelectScreen;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.characters.FireLionMap;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_7 extends AbstractChapter {

    final int[] overLayers = { 15 };
    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_7(Unsealed game) {
        super(game);
        mapname="TownOneNight";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.TOWN );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(1524,1608);
        tmpChar.setDirection(MapCharacter.dirLeft);
        tmpChar.updateAnimation();
        characters.add(tmpChar);

        tmpChar = new Kid(getAtlas(),0);
        tmpChar.setPosition(1024,1608);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.setWalking(true);
        characters.add(tmpChar);

        tmpChar = new FireLionMap(getAtlas());
        tmpChar.setPosition(1350,1590);
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
                        dialog.setText("Lidia: It's always hard to say goodbye.\n");
                        dialog.setVisible(true);
                        break;
                    case 1:
                        dialog.setText("Mimi: Wait! Don't go!");
                        act = 2;
                        break;
                    case 28:
                        character.setDirection(MapCharacter.dirRight);
                        character.setWalking(true);
                        act = 29;
                        break;
                    case 29:
                        if(character.getX()<1730){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            actions = sequence(fadeOut(0.85f));
                            character.addAction(actions);
                            act = 30;
                        }
                        break;
                    case 30:
                        game.setScreen( new ChapterSelectScreen( game ) );
                        break;
                }
            }
            if(character instanceof Kid){
                switch(act){
                    case 2:
                        if(character.getX()<1480){
                            character.setX(character.getX()+2);
                        }else{
                            character.setWalking(false);
                        }
                        break;
                    case 3:
                        if(character.getX()!=1480){
                            act = 2;
                        }else{
                           act = 4;
                        }
                        break;
                    case 4:
                        dialog.setText("Lidia: Sorry, but I've already stayed more than the week I promised to.");
                        break;
                    case 5:
                        dialog.setText("Mimi: Thank you for everything you've done.\n" +
                        		"You've really changed everyone in town.");
                        break;
                    case 6:
                        dialog.setText("Lidia: I'm glad to hear that. ^_^\n" +
                        		"Just don't forget about the laws!");
                        break;
                    case 7:
                        dialog.setText("Mimi: I know! I know! They grant us our Four Freedoms of magic!");
                        break;
                    case 8:
                        dialog.setText("Mimi: The Freedom to cast any spell. \n" +
                        		"The Freedom to study the spell, and weave it into something new!\n" +
                        		"The Freedom to teach others how to do the spell\n" +
                        		"And the Freedom to let others pass on the knowledge");
                        break;
                    case 9:
                        dialog.setText("Mimi: I've heard the grownups say that we'll join the fight against Xios.");
                        break;
                    case 10:
                        dialog.setText("Lidia: Then my job here is done.");
                        break;
                    case 11:
                        dialog.setText("Mimi: Before you go, there's one thing I'd like to show you... ");
                        break;
                    case 12:
                        dialog.setVisible(false);
                        character.setDirection(MapCharacter.dirLeft);
                        character.setWalking(true);
                        act = 13;
                        break;
                    case 13:
                        if(character.getX()>1300){
                            character.setX(character.getX()-1);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            character.setWalking(false);
                            actions = sequence(delay(0.5f),color(Color.RED),delay(0.3f),color(Color.WHITE),delay(0.3f),run(new Runnable() {
                                @Override
                                public void run() {
                                    setAct(14);
                                    
                                }
                            }));
                            character.addAction(actions);
                        }
                        break;
                    case 15:
                        actions = sequence(delay(0.6f),color(Color.RED),delay(0.3f),color(Color.WHITE),delay(0.3f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(16);
                                
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 17:
                        actions = sequence(delay(0.5f),color(Color.RED),delay(0.3f),color(Color.WHITE),delay(0.3f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(18);
                                
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 19:
                        actions = sequence(delay(0.5f),color(Color.RED),delay(0.3f),color(Color.WHITE),delay(0.3f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(20);
                                
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 21:
                        dialog.setText("Lidia: Wow! Great job!");
                        dialog.setVisible(true);
                        break;
                    case 22:
                        dialog.setText("Mimi: Thank you! I've been working very hard!\n" +
                        		"I wanted to give you something in return for all the things you've done for us.\n" +
                        		"I've already Enchanted the spell, so that the spell cannot be forgotten");
                        break;
                    case 23:
                        dialog.setText("Lidia learned Fire Lion! You can now use the Spell in battle");
                        break;
                    case 24:
                        dialog.setText("Lidia: Thank you Mimi, I'll never forget you.");
                        break;
                    case 25:
                        dialog.setText("Mimi: When you have time, could you head to Ryuken? We haven't heard from them in a long time.\n" +
                        		"It's just past the desert. ");
                        break;
                    case 26:
                        dialog.setText("Lidia: Sure. I'll check out the town.");
                        break;
                    case 27:
                        dialog.setVisible(false);
                        act = 28;
                        break;
                }
            }
            if(character instanceof FireLionMap){
                switch(act){
                    case 14:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirDown);
                            setAct(15);
                            character.setPosition(1350,1590);
                        }
                        break;
                    case 16:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setPosition(1350,1590);
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirLeft);
                            setAct(17);
                        }
                        break;
                    case 18:
                        character.setVisible(true);
                        if(character.getX()<1450){
                            character.setX(character.getX()+2);
                        }else{
                            character.setPosition(1350,1590);
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirRight);
                            setAct(19);
                        }
                        break;
                    case 20:
                        character.setVisible(true);
                        if(character.getX()<1460){
                            character.setX(character.getX()+1);
                        }else{
                            character.setVisible(false);
                            character.setDirection(MapCharacter.dirRight);
                            setAct(21);
                        }
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
