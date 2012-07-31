package net.k3rnel.unsealed.story.chapters;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Dummy;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.story.characters.Kid;
import net.k3rnel.unsealed.story.helpers.MapCharacter;

public class Chapter1_3 extends BattleScreen {


     public Chapter1_3(Unsealed game) {
        super(game,true,"TownOne");
        
     }
   
     @Override
    public void show() {
        super.show();
        
        camera.position.set(1300,1220, 0);
        camera.update();
        
        hero.setSkill2(new EarthSpikes(getAtlas()));
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
                dialog.setText("Skills can be used with J K L");
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
                dialog.setText("New Skills will be unlocked as the game progresses. ");
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
                dialog.setText("Kids: Wow! That was impressive!");
                if(stateTime>6){
                    act=15;
                }
                break;
            case 15:
                dialog.setVisible(false);
                act = 16;
                break;
            case 16:
                game.setScreen(new Chapter1_4(game));
                break;
        }
    }
}
