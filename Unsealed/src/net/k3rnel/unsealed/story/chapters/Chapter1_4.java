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

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.story.characters.Citizen;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.characters.Lidia;
import net.k3rnel.unsealed.story.characters.Snake;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

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
                                "My name is Lidia Terius, and I came here to explain some things...");
                        dialog.setVisible(true);
                        break;
                    case 1:
                        dialog.setText("Townsfolk Ramsey: Are you the reason some have started to use magic again?");
                        break;
                    case 2:
                        dialog.setText("Townsfolk Hashbang: My daughter has been doing all sorts of fire magic!\n" +
                        		"It's really dangerous, she could get hurt!");
                        break;
                    case 3:
                        dialog.setText("Townsfolk Terranova: I think we should hear her out, I'm interested in what she has to say.");
                        break;
                    case 4:
                        //TODO: Expand on Xios
                        dialog.setText("Lidia: *Ahem* Let's see... About 300 years ago, everyone was capable of using Magic, like Mimi\n" +
                        		"That was until Xios decided he wanted to be the only one using magic, and sealed the Pixies\n" +
                        		"The Pixies are the source of magic of the land. I recently unsealed the one that protects this area.");
                        break;
                    case 5:
                        dialog.setText("Townsfolk Forthwind: Under whose authority?");
                        break;
                    case 6:
                        dialog.setText("Lidia: The Free Spellweaver Foundation. \n" +
                        		"They are Great and Powerful Sorcerers that help and guide everyone regarding the uses of magic");
                        break;
                    case 7:
                        dialog.setText("Lidia: Their rules are quite simple, if you allow me to explain them -");
                        break;
                    case 8:
                        dialog.setVisible(false);
                        setAct(9);
                        break;
                    case 11:
                        dialog.setText("Lidia: Stay calm, everyone, I'll handle this");
                        break;
                    case 12:
                        dialog.setVisible(false);
                        character.setWalking(true);
                        act = 13;
                        break;
                    case 13:
                        if(character.getY()>1240){
                            character.setY(character.getY()-2);
                            centerCamera(character);                            
                        }else{
                            character.setDirection(MapCharacter.dirLeft);
                            act = 14;
                        }
                        break;
                    case 14:
                        if(character.getX()>600){
                            character.setX(character.getX()-2);
                            centerCamera(character);
                        }else{
                            character.setDirection(MapCharacter.dirDown);
                            act = 15;
                        }
                        break;
                    case 15:
                        if(character.getY()>500){
                            character.setY(character.getY()-2);
                            centerCamera(character); 
                        }else{
                            character.setWalking(false);
                            character.setDirection(MapCharacter.dirLeft);
                            act =16;
                            
                        }
                        break;
                    case 16:
                        character.setDirection(MapCharacter.dirRight);
                        
                        act = 17;
                        
                       break;
                    case 17:
                        character.setDirection(MapCharacter.dirDown);
                        act = 18;
                        break;
                    case 18:
                        dialog.setText("Lidia: This is gonna be fun");
                        dialog.setVisible(true);
                        break;
                    case 19:
                        dialog.setVisible(false);
                        game.setScreen( new Chapter1_5( game ) );
                        break;
                }
            }
            if(character instanceof Kid){
                switch(act){
                    case 5:
                        character.setDirection(MapCharacter.dirLeft);
                        break;
                }
            }
            if(character instanceof Citizen){
                if(((Citizen)character).citizen!=10){
                    switch(act){
                        case 10:
                            character.setDirection(MapCharacter.dirLeft);
                            break;
                    }
                }
                if(((Citizen)character).citizen==10){
                    switch(act){
                        case 9:
                            if(character.getY()<1140){
                                character.setY(character.getY()+2);
                            }else{
                                character.setWalking(false);
                                act = 10;
                            }
                            break;
                        case 10:
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
