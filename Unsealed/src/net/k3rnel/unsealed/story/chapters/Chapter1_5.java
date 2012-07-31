package net.k3rnel.unsealed.story.chapters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Snake;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.screens.BattleScreen;

public class Chapter1_5 extends BattleScreen {

    protected Button restartButton;

    public Chapter1_5(Unsealed game) {
        super(game,true, "TownOne");

    }

    @Override
    public void show() {
        super.show();
        hero.setSkill2(new EarthSpikes(getAtlas()));
        hud.bButton.addActor(hero.getSkill2());
        
        restartButton = new TextButton("Restart?", getSkin());
        restartButton.setY(250);
        restartButton.setX(320);
        restartButton.setWidth(100);
        restartButton.setHeight(50);
        restartButton.setVisible(false);
        restartButton.setDisabled(true);
        restartButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent arg0, float arg1, float arg2) {
               act = -1;
               hero.setHp(150);
               hero.setMana(0);
               hero.setGrid(1,1);
               hero.setSkill2(new EarthSpikes(getAtlas()));
               hud.bButton.addActor(hero.getSkill2());
               grid.assignEntity(hero);
               restartButton.setVisible(false);
            }
        });
        this.stage.addActor(restartButton);
        camera.position.set(700, 660, 0);
        camera.update();
    }
    
    @Override
    public void checkScene(float delta){
        this.stateTime+=delta;
       
        switch(act){
            case -1:
                dialog.setVisible(true);
                grid.reset();
               
                dialog.setText("I need to concentrate and use my Skills appropriately");
                if(stateTime>4){
                    hero.setHp(150);
                    hero.setMana(0);
                    grid.assignEntity(hero);
                    act = 0;
                }
                break;
            case 0:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Snake(getAtlas(),50,3,0),new Snake(getAtlas(),60,4,1));
                act = 1;
                break;
            case 1:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 2;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 2:
                dialog.setText("I can't let my guard down. Here come some more!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=3;
                }
                break;
            case 3:
                dialog.setVisible(false);
                grid.reset();
                hero.setMana(0);
                grid.assignEntity(hero);
                grid.spawnEnemies(new Snake(getAtlas(),50,3,1),new Snake(getAtlas(),60,4,0),new Snake(getAtlas(),70,5,2));
                act = 4;
                break;
            case 4:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 5;
                    stateTime = 0;
                }else if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 5:
                dialog.setText("Ugh... how many are there?");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=6;
                }
                break;
            case 6:
                dialog.setVisible(false);
                grid.reset();
                hero.setMana(0);
                grid.assignEntity(hero);
                grid.spawnEnemies(new Snake(getAtlas(),70,3,1),new Snake(getAtlas(),70,4,0),new Snake(getAtlas(),70,4,1),new Snake(getAtlas(),80,5,2),new Snake(getAtlas(),80,5,0));
                act = 7;
                stateTime = 0;
                break;
            case 7:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 8;
                    stateTime = 0;
                }else if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 8:
                dialog.setText("Looks like this is the last batch of them!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=9;
                }
                break;
            case 9:
                dialog.setVisible(false);
                grid.reset();
                hero.setMana(0);
                grid.assignEntity(hero);
                grid.spawnEnemies(new Snake(getAtlas(),80,3,1),new Snake(getAtlas(),80,4,1),new Snake(getAtlas(),70,4,2),new Snake(getAtlas(),80,5,1),new Snake(getAtlas(),100,5,0));
                act = 10;
                stateTime = 0;
                break;
            case 10:
                if(BattleGrid.checkState()==BattleGrid.battleWon&&stateTime>5){
                    act = 11;
                    stateTime = 0;
                }else if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>5){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 11:
                dialog.setText("The town should be safe now. I'll let them know.");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=12;
                }
                break;
            case 12:
                game.setScreen( new Chapter1_6( game ) );
                break;

        }
    }
}
