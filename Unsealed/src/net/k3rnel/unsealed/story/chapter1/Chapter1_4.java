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

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.AbstractChapter;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Citizen;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Snake;

public class Chapter1_4 extends AbstractChapter {


    /**
     * Chapter One: New Girl in Town
     * @param game
     */
    public Chapter1_4(Unsealed game) {
        super(game);
        mapname="TownOne";
    }

    @Override
    public void show() {
        super.show();

        game.getMusicManager().play( UnsealedMusic.TOWN );
        
        tmpChar = new Lidia(getAtlas());
        tmpChar.setPosition(815,1300);
        tmpChar.updateAnimation();
        characters.add(tmpChar);

        tmpChar = new Kid(getAtlas(),0);
        tmpChar.setPosition(822,1230);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Kid(getAtlas(),1);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.setPosition(922,1230);
        characters.add(tmpChar);

        tmpChar = new Kid(getAtlas(),2);
        tmpChar.setPosition(728,1230);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(700,1180);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(780,1180);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(860,1180);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(940,1180);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(990,1230);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(910,1120);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(830,1120);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(750,1120);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);
        tmpChar = new Citizen(getAtlas(),0);
        tmpChar.setPosition(650,1230);
        tmpChar.setDirection(MapCharacter.dirUp);
        characters.add(tmpChar);

        tmpChar = new Citizen(getAtlas(),10);
        tmpChar.setPosition(630,900);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.setWalking(true);
        characters.add(tmpChar);
        
        tmpChar = new Snake(getAtlas());
        tmpChar.setPosition(700,300);
        tmpChar.setDirection(MapCharacter.dirUp);
        tmpChar.setWalking(true);
        characters.add(tmpChar);
        
        tmpChar = new Snake(getAtlas());
        tmpChar.setPosition(740,500);
        tmpChar.setDirection(MapCharacter.dirLeft);
        tmpChar.setWalking(true);
        characters.add(tmpChar);
        
        tmpChar = new Snake(getAtlas());
        tmpChar.setPosition(500,500);
        tmpChar.setDirection(MapCharacter.dirRight);
        tmpChar.setWalking(true);
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
                        dialog.setText("Lidia: Thank you for coming everyone!\n" +
                                "My name is Lidia Terius, I am a Spellweaver. I came here to explain some things -");
                        dialog.setVisible(true);
                        break;
                    case 1:
                        dialog.setText("Townsfolk Ramsey: Are you the reason some have started to use magic again?");
                        break;
                    case 2:
                        dialog.setText("Townsfolk Hashbang: My daughter has been doing all sorts of fire magic.\n" +
                        		"It's really dangerous, she could get hurt!");
                        break;
                    case 3:
                        dialog.setText("Townsfolk Terranova: I think we should hear her out, I'm interested in what she has to say.\n" +
                        		"This town used to be kind to Spellweavers");
                        break;
                    case 4:
                        dialog.setText("Townsfolk Calico: That was before Xios outlawed magic! This town has suffered enough from Xios,\n" +
                        		"She shouldn't be welcome here.");
                        break;
                    case 5:
                        dialog.setText("Lidia: *Ahem* Things have changed now. Everyone is now able to use magic, just like Mimi\n" +
                        		"What Xios did was wrong. He sealed up your source of magic, locking away your abilities.\n" +
                        		"Imagine being petrified for 300 years, unable to move. Unable to protect those you were supposed to.\n");
                        break;
                    case 6:
                        dialog.setText("Lidia: I unsealed Arch, your Guardian Penguin.\n" +
                        		"He was adorable! The poor creature didn't deserve being sealed up. \n");
                        break;
                    case 7:
                        dialog.setText("Townsfolk Forthwind: Under whose authority?");
                        break;
                    case 8:
                        dialog.setText("Lidia: The Free Spellweaver Foundation. \n" +
                        		"They are Great and Powerful Spellweavers that help and guide everyone regarding the uses of magic\n" +
                        		"I was sent to Altera to Unseal the Penguins and restore the magic to the land");
                        break;
                    case 9:
                        dialog.setText("Townsfolk Hashbang: What do you mean you're not from Altera?");
                        break;
                    case 10:
                        dialog.setVisible(false);
                        setAct(11);
                        break;
                    case 13:
                        dialog.setText("Lidia: Stay calm, everyone, I'll handle this");
                        break;
                    case 14:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 15;
                        break;
                    case 15:
                        if(character.getY()>1240){
                            character.setY(character.getY()-2);
                            centerCamera(character);                            
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 16;
                        }
                        break;
                    case 16:
                        if(character.getX()>600){
                            character.setX(character.getX()-2);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirDown);
                            act = 17;
                        }
                        break;
                    case 17:
                        if(character.getY()>500){
                            character.setY(character.getY()-2);
                            centerCamera(character); 
                        }else{
                            character.setWalking(false);
                            character.setDirection(MapCharacter.dirLeft);
                            act =18;
                            
                        }
                        break;
                    case 18:
                        character.setDirection(MapCharacter.dirRight);
                        
                        act = 19;
                        
                       break;
                    case 19:
                        character.setDirection(MapCharacter.dirDown);
                        act = 20;
                        break;
                    case 20:
                        dialog.setText("Lidia: This is gonna be fun");
                        dialog.setVisible(true);
                        break;
                    case 21:
                        dialog.setVisible(false);
                        game.setScreen( new Chapter1_5( game ) );
                        break;
                }
            }
            if(character instanceof Kid){
                switch(act){
                    case 12:
                        character.setDirection(MapCharacter.dirLeft);
                        break;
                }
            }
            if(character instanceof Citizen){
                if(((Citizen)character).citizen!=10){
                    switch(act){
                        case 12:
                            character.setDirection(MapCharacter.dirLeft);
                            break;
                    }
                }
                if(((Citizen)character).citizen==10){
                    switch(act){
                        case 11:
                            if(character.getY()<1140){
                                character.setY(character.getY()+2);
                            }else{
                                character.setWalking(false);
                                act = 12;
                            }
                            break;
                        case 12:
                            dialog.setText("EEEEEEEEKKKKKKKKKKK!\n" +
                                    "Please Help! Hurry! \n" +
                                    "Snakes are invading our crops again! ");
                            dialog.setVisible(true);
                            break;
                       
                    }
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
