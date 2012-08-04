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
package net.k3rnel.unsealed.story.chapter3;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.FakeXios;
import net.k3rnel.unsealed.battle.enemies.GNU;
import net.k3rnel.unsealed.battle.enemies.Xios;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.battle.skills.FireLion;
import net.k3rnel.unsealed.battle.skills.IceTentacle;
import net.k3rnel.unsealed.battle.skills.SuperFirePunch;
import net.k3rnel.unsealed.battle.skills.ThunderClaw;
import net.k3rnel.unsealed.battle.skills.TornadoVacuum;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.screens.ChapterSelectScreen;
import net.k3rnel.unsealed.screens.CreditsScreen;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;

public class Chapter3_8 extends BattleScreen {

    protected ImageButton restartButton;

    public Chapter3_8(Unsealed game) {
        super(game,true, "Boss Arena");

    }

    @Override
    public void show() {
        super.show();
        game.getMusicManager().play( UnsealedMusic.BATTLE );
        hero.setHp(300);
        hero.setSkill1(new EarthSpikes(getAtlas()));
        
        hero.setSkill2(new SuperFirePunch(getAtlas()));
        
        hero.setSkill3(new ThunderClaw(getAtlas()));
        
        hero.setSkill4(new FireLion(getAtlas()));
        hero.setSkill5(new IceTentacle(getAtlas()));
        hero.setSkill6(new TornadoVacuum(getAtlas()));
        
        buttonPress(9,true);
        buttonPress(9,true);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/ui/continue" );
        restartButton = new ImageButton(new Image(atlasRegion).getDrawable(),new Image(atlasRegion).getDrawable());
        restartButton.setY(140);
        restartButton.setX(170);
        restartButton.setWidth(426);
        restartButton.setHeight(165);
        restartButton.setVisible(false);
        restartButton.setDisabled(true);
        restartButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent arg0, float arg1, float arg2) {
               act = -1;
               hero.reset();
               hero.setHp(300);
               hero.setMana(0);
               hero.setGrid(1,1);
               hero.setSkill1(new EarthSpikes(getAtlas()));
               
               hero.setSkill2(new SuperFirePunch(getAtlas()));
               
               hero.setSkill3(new ThunderClaw(getAtlas()));
               
               hero.setSkill4(new FireLion(getAtlas()));
               hero.setSkill5(new IceTentacle(getAtlas()));
               hero.setSkill6(new TornadoVacuum(getAtlas()));
               buttonPress(9,true);
               buttonPress(9,true);
               grid.reset();
               grid.assignEntity(hero);
               restartButton.setVisible(false);
            }
        });
        this.stage.addActor(restartButton);
        camera.position.set(1050, 1960, 0);
        camera.update();
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        camera.position.set(1000, 1000, 0);
        camera.zoom = 100f;
        camera.update();
        if(restartButton.isVisible()){
            this.getBatch().begin();
            restartButton.draw(this.getBatch(), 1);
            this.getBatch().end();
        }
    }
    @Override
    public void checkScene(float delta){
        this.stateTime+=delta;
        camera.position.set(510, 280, 0);
        camera.zoom = 1f;
        camera.update();
        switch(act){
            case -1:
                buttonPress(9,true);
                buttonPress(9,true);
                dialog.setVisible(true);
                dialog.setText("I need to concentrate and use my Skills appropriately");
                if(stateTime>4){
                   stateTime = 0;
                   act = 0;
                }
                break;
            case 0:
                dialog.setText("This is the final boss battle. Good luck!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act = 1;
                    stateTime = 0;
                }
                break;
            case 1:
                disableInput = false;
                dialog.setVisible(false);
                grid.spawnEnemies(new Xios(getAtlas(),1337,4,1));
                act = 2;
                stateTime = 0;
                break;
            case 2:
                dialog.setVisible(true);
                if(stateTime>0&&stateTime<4){
                    dialog.setText("Xios: You are no match for me, puny human!");
                }else if(stateTime>4&&stateTime<8){
                    dialog.setText("Lidia: Everyone's hope is on me. I will defeat you!");
                }else if(stateTime>8&&stateTime<12){
                    dialog.setVisible(false);
                }
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 3;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 3:
                disableInput=true;
                grid.reset();
               
                act = 4;
                stateTime = 0;
                break;
            case 4:
                dialog.setText("Lidia: Now that I've defeated you... You shall be punished for your crimes!\n" +
                		"By the power vested in me by the Free Spellweaver Foundation, " +
                		"I, Lidia Terius, Vanish you to Dev Null! ");
                dialog.setVisible(true);
                if(stateTime>4){
                    act = 5;
                    stateTime = 0;
                }
                break;
            case 5:
                hero.setHp(0);
                grid.spawnEnemies(false,new GNU(getAtlas(),500,1,1));
                grid.spawnEnemies(false,new FakeXios(getAtlas(),1,4,1));
                dialog.setText("Xios: NOOOOOOOOOOOOOOOOOOOOOOOOO!");
                if(stateTime>4){
                    act = 11;
                    stateTime = 0;
                }
                break;
            case 11:
                dialog.setText("Evil Xios has been slayed!\n" +
                		"Magic is Free to be used by everyone!");
                game.getMusicManager().play( UnsealedMusic.VICTORY );
                dialog.setVisible(true);
                if(stateTime>5){
                    act = 12;
                    stateTime = 0;
                }
                break;
            case 12:
                game.getMusicManager().play( UnsealedMusic.MENU );
                game.setScreen(new CreditsScreen(game));
                break;
        }
    }
}
