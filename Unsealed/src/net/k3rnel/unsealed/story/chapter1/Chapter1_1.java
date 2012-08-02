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
package net.k3rnel.unsealed.story.chapter1;

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
import net.k3rnel.unsealed.story.characters.Penguin;
import net.k3rnel.unsealed.story.characters.Whisperer;

public class Chapter1_1 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_1(Unsealed game) {
        super(game);
        mapname="RouteOneDungeon";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.CAVE );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(115,140);
        tmpChar.updateAnimation();
        characters.add(tmpChar);
        tmpChar = new Whisperer(getAtlas());
        tmpChar.updateAnimation();
        tmpChar.addAction(fadeIn(0.75f));
        tmpChar.setPosition(291,470);
        tmpChar.setVisible(false);
        characters.add(tmpChar);
        tmpChar = new Penguin(getAtlas());
        tmpChar.updateAnimation();
        tmpChar.setPosition(87,660);
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
                        character.getColor().a = 0;
                        character.setWalking(false);
                        actions = sequence(fadeIn(0.75f),delay(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(1);
                            }
                        }));
                        character.addAction(actions);
                        character.setWalking(true);
                        break;
                    case 1:
                        if(character.getY()>120){
                            character.setY(character.getY()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirRight);
                            setAct(2);
                        }
                        break;
                    case 2:
                        if(character.getX()<291){
                            character.setX(character.getX()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(3);
                        }
                        break;
                    case 3:
                        if(character.getY()<400){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            character.setWalking(false);
                            setAct(4);
                        }
                        break;
                    case 19:
                        if(character.getY()<470){
                            character.setWalking(true);
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            setAct(20);
                        }
                        break;
                    case 20:
                        if(character.getX()>70){
                            character.setX(character.getX()-1);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirUp);
                            setAct(21);
                        }
                        break;
                    case 21:
                        if(character.getY()<580){
                            character.setY(character.getY()+1);
                            centerCamera(character);
                        }else{
                            character.setWalking(false);
                            setAct(22);
                        }
                        break;
                    case 22:
                        dialog.setText("Lidia: You poor thing....\n" +
                        		"Awaken from your slumber. Let the seal be broken.\n" +
                        		"I, Lidia Terious, UNSEAL YOU!");
                        dialog.setVisible(true);
                        break;
                    case 23:
                        dialog.setVisible(false);
                        setAct(24);
                        break;

                }
            }
            if(character instanceof Whisperer){
                switch(act){
                    case 4:
                        character.getColor().a = 0;
                        actions = sequence(fadeIn(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(5);
                            }
                        }));
                        character.addAction(actions);
                        character.setVisible(true);
                        break;
                    case 5:
                        dialog.setText("Lidia: I was wondering when you'd show up. ");
                        dialog.setVisible(true);
                        break;
                    case 6:
                        dialog.setText("Whisperer: You know I am but a mere whisper away from being summoned");
                        break;
                    case 7:
                        dialog.setText("Lidia: I found the next Guardian. It's up ahead, I can sense it.");
                        break;
                    case 8:
                        dialog.setText("Whisperer: It is calling out for you. It's been sealed for so long. ");
                        break;
                    case 9:
                        dialog.setText("Lidia: If I was sealed for 300 years I wouldn't just whisper for help... I'd shout too.");
                        break;
                    case 10:
                        dialog.setText("Lidia: The locals think of them as just statues, they've forgotten that they are much more than that.\n" +
                        		"It's always up to a Traveler to set things right and undo their damage!");
                        break;
                    case 11:
                        dialog.setText("Whisperer: You sound weary. This'll be the Fifth Guardian and you're already tired?");
                        break;
                    case 12:
                        dialog.setText("Lidia: You say it as if it were easy. You simply appear and disappear at the slightest sign of trouble.\n" +
                        		"After what happened at Marblehead, I'm taking things a little more calmed this time.");
                        break;
                    case 13:
                        dialog.setText("Whisperer: Marblehead was different. New Lion has always allied with Spellweavers");
                        break;
                    case 14:
                        dialog.setText("Lidia: And apparently, Marblehead has always been against Spellweavers!\n" +
                        		"You should've warned me!");
                        break;
                    case 15:
                        dialog.setText("Whisperer: You were distracted...");
                        break;
                    case 16:
                        dialog.setVisible(false);
                        actions = sequence(fadeOut(0.75f),run(new Runnable() {
                            @Override
                            public void run() {
                                setAct(17);
                            }
                        }));
                        character.addAction(actions);
                        break;
                    case 17:
                        dialog.setText("Lidia: She just *had* to bring that up again...  ");
                        dialog.setVisible(true);
                        break;
                    case 18:
                        dialog.setVisible(false);
                        act = 19;
                        break;
                }
            }
            if(character instanceof Penguin){
                switch(act){
                    case 24:
                        actions = sequence(fadeOut(0.75f),
                                run(new Runnable() {
                                    @Override
                                    public void run() {
                                        setAct(25);
                                    }
                                }),fadeIn(0.75f));
                        character.addAction(actions);
                        break;
                    case 25:
                        character.setWalking(true);
                        setAct(26);
                        break;
                    
                    case 26:
                        dialog.setText("Penguin: *Squeeak*!" +
                        		"Thank you, Traveler! I've been calling out for so long!\n" +
                        		"I am Archimides, The Guardian of this area.");
                        dialog.setVisible(true);
                        break;
                    case 27:
                        dialog.setText("Penguin: Now that I've been unsealed, magic will return to this area.\n" +
                        		"Thank you for your help");
                        break;
                    case 28:
                        dialog.setText("Lidia: I'm glad to hear that. I'll talk with the people of New Lion to let them know");
                        break;                        
                    case 29:
                        dialog.setVisible(false);
                        game.setScreen( new Chapter1_2( game ) );
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
