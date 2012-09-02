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
package net.k3rnel.unsealed.screens;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Dummy;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.battle.skills.FireLion;
import net.k3rnel.unsealed.battle.skills.FirePunch;
import net.k3rnel.unsealed.battle.skills.IceTentacle;
import net.k3rnel.unsealed.battle.skills.ThunderClaw;
import net.k3rnel.unsealed.battle.skills.TornadoVacuum;
import net.k3rnel.unsealed.screens.BattleScreen;

public class TutorialDesktopScreen extends BattleScreen {


     public TutorialDesktopScreen(Unsealed game) {
        super(game,true,"Boss Arena");
        
     }
   
     @Override
    public void show() {
        super.show();
        
        camera.position.set(510, 305, 0);
        camera.zoom = 0.8f;
        camera.update();
        hero.setSkill1(new FirePunch(getAtlas()));
        hero.setSkill2(new EarthSpikes(getAtlas()));
        hero.setSkill3(new ThunderClaw(getAtlas()));
        hero.setSkill4(new IceTentacle(getAtlas()));
        hero.setSkill5(new TornadoVacuum(getAtlas()));
        hero.setSkill6(new FireLion(getAtlas()));
        buttonPress(9,true);
        buttonPress(9,true);     
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
                    buttonPress(6,true);
                    disableInput = true;
                    stateTime = 0;
                    act = 10;
                }
                break;
            case 10:
                dialog.setText("You can switch spells by pressing I");
                if(stateTime>5){
                    buttonPress(9,true);
                    stateTime = 0;
                    act = 11;
                }
                break;
            case 11:
                if(stateTime>4){
                    buttonPress(9,true);
                    stateTime = 0;
                    act = 12;
                }
                break;
            case 12:
                dialog.setText("Now, lets test these abilities! ");
                if(stateTime>4){
                    stateTime = 0;
                    act = 13;
                }
                break;
               
            case 13:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Dummy(getAtlas(),3,1),new Dummy(getAtlas(),4,1),new Dummy(getAtlas(),5,2),new Dummy(getAtlas(),5,0));
                disableInput = false;
                act = 14;
                break;
            case 14:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 15;
                    stateTime = 0;
                }
                break;
            case 15:
                dialog.setVisible(false);
                act = 16;
                break;
            case 16:
                game.setScreen(new MenuScreen(game));
                break;
        }
    }
}
