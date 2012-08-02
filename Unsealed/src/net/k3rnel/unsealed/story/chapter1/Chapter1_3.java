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
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Dummy;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.story.MapCharacter;
import net.k3rnel.unsealed.story.characters.Kid;

public class Chapter1_3 extends BattleScreen {


     public Chapter1_3(Unsealed game) {
        super(game,true,"TownOne");
        
     }
   
     @Override
    public void show() {
        super.show();
        
        camera.position.set(1300,1220, 0);
        camera.update();
        hero.setSkill1(null);
        hero.setSkill2(new EarthSpikes(getAtlas()));
        hero.setSkill3(null);
        hero.setSkill4(null);
        hero.setSkill5(new EarthSpikes(getAtlas()));
        hero.setSkill6(null);
        buttonPress(9,true);
        hud.bButton.addActor(hero.getSkill2());
        MapCharacter character = new Kid(getAtlas(),0);
        character.setVisible(true);
        character.setPosition(300,270);
        character.setDirection(MapCharacter.dirDown);
        character.updateAnimation();
        characters.add(character);
        
        character = new Kid(getAtlas(),1);
        character.setVisible(true);
        character.setPosition(400,270);
        character.setDirection(MapCharacter.dirDown);
        character.updateAnimation();
        characters.add(character);
        
        character = new Kid(getAtlas(),2);
        character.setVisible(true);
        character.setPosition(350,270);
        character.setDirection(MapCharacter.dirDown);
        character.updateAnimation();
        characters.add(character);
        
    }
    @Override
    public void checkScene(float delta){
        this.stateTime+=delta;
        switch(act){
            case 0:
                disableInput = true;
                dialog.setText("You can move around using the W A S D keys on your keyboard");
                dialog.setVisible(true);
                stateTime = 0;
                act = 1;
                break;
            case 1:
                if(stateTime>2){
                    disableInput = false;
                    buttonPress(0,false);
                    disableInput = true;
                    act = 2;
                    stateTime = 0;
                }
            case 2:
                if(stateTime>2){
                    disableInput = false;
                    buttonPress(2,false);
                    disableInput = true;
                    act = 3;
                    stateTime = 0;
                }
                break;
            case 3:
                if(stateTime>2){
                    disableInput = false;
                    buttonPress(1,false);
                    disableInput = true;
                    act = 4;
                    stateTime = 0;
                }
                break;
            case 4:
                if(stateTime>2){
                    disableInput = false;
                    buttonPress(3,false);
                    disableInput = true;
                    act = 5;
                    stateTime = 0;
                }
                break;
            case 5:
                dialog.setText("You can shoot enemies using O");
                if(stateTime>3){
                    disableInput = false;
                    buttonPress(5,false);
                    disableInput = true;
                    stateTime = 0;
                    act = 6;
                }
                break;
            case 6:
                if(stateTime>3){
                    disableInput = false;
                    buttonPress(5,false);
                    disableInput = true;
                    stateTime = 0;
                    act = 7;
                }  
                break;
            case 7:
                dialog.setText("You can use a Shield using U");
                if(stateTime<4){
                    disableInput = false;
                    buttonPress(4,true);
                    disableInput = true;
                    
                }else{
                    disableInput = false;
                    buttonPress(4,false);
                    disableInput = true;
                    act = 8;
                    stateTime = 0;
                }
                break;
            case 8:
                dialog.setText("Spells can be used with J K L");
                hero.setMana(20);
                act = 9;
                disableInput = false;
                buttonPress(7,true);
                disableInput = true;
                break;
            case 9:
                if(stateTime>3){
                    disableInput = false;
                    buttonPress(7,true);
                    disableInput = true;
                    stateTime = 0;
                    act = 10;
                }
                break;
            case 10:
                dialog.setText("New spells will be unlocked as the game progresses. ");
                if(stateTime>3){
                    stateTime = 0;
                    act = 11;
                }
                break;
            case 11:
                dialog.setText("Now, lets test these abilities! ");
                if(stateTime>4){
                    stateTime = 0;
                    act = 12;
                }
                break;
            case 12:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Dummy(getAtlas(),3,1),new Dummy(getAtlas(),4,1),new Dummy(getAtlas(),5,2),new Dummy(getAtlas(),5,0));
                disableInput = false;
                act = 13;
                break;
            case 13:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 14;
                    stateTime = 0;
                }
                break;
            case 14:
                dialog.setVisible(true);
                dialog.setText("Kids: Wow! That was impressive!\n" +
                		"Mimi: Looks like someone's coming!\n" +
                		"Leon: Brace yourselves, the grownups are coming!");
                if(stateTime>6){
                    stateTime = 0;
                    act=15;
                }
                break;
            case 15:
                dialog.setText("Lidia: Ah, great. It's time I've talked with everyone here.");
                if(stateTime>6){
                    act=16;
                }
                break;
            case 16:
                dialog.setVisible(false);
                act = 17;
                break;
            case 17:
                game.setScreen(new Chapter1_4(game));
                break;
        }
    }
}
