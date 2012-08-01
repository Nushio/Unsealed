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

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Bee;
import net.k3rnel.unsealed.battle.enemies.Ghost;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.battle.skills.FireLion;
import net.k3rnel.unsealed.battle.skills.IceTentacle;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;

public class Chapter2_9 extends BattleScreen {

    protected Button restartButton;

    public Chapter2_9(Unsealed game) {
        super(game,true, "Asia Town");

    }

    @Override
    public void show() {
        super.show();
        game.getMusicManager().play( UnsealedMusic.BATTLE );
        
        hero.setHp(230);
        hero.setSkill1(new EarthSpikes(getAtlas()));
        hero.setSkill2(new IceTentacle(getAtlas()));
        hero.setSkill3(new FireLion(getAtlas()));
        hero.setSkill4(new EarthSpikes(getAtlas()));
        hero.setSkill5(new IceTentacle(getAtlas()));
        hero.setSkill6(new FireLion(getAtlas()));
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
               hero.setHp(230);
               hero.setMana(0);
               hero.setGrid(1,1);
               hero.setSkill1(new EarthSpikes(getAtlas()));
               hero.setSkill2(new IceTentacle(getAtlas()));
               hero.setSkill3(new FireLion(getAtlas()));
               hero.setSkill4(new EarthSpikes(getAtlas()));
               hero.setSkill5(new IceTentacle(getAtlas()));
               hero.setSkill6(new FireLion(getAtlas()));
               hero.reset();
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
    public void checkScene(float delta){
        this.stateTime+=delta;
        
        switch(act){
            case -1:
                dialog.setVisible(true);
                dialog.setText("I need to concentrate and use my Skills appropriately");
                if(stateTime>4){
                    act = 0;
                    stateTime = 0;
                }
                break;
            case 0:
                dialog.setText("Remember you can use your Shield using U");
                dialog.setVisible(true);
                if(stateTime>4){
                    act = 1;
                    stateTime = 0;
                }
                break;
            case 1:
                disableInput = false;
                dialog.setVisible(false);
                hero.setMana(0);
                grid.spawnEnemies(new Ghost(getAtlas(),100,3,0),new Ghost(getAtlas(),100,3,2),new Bee(getAtlas(),70,4,1));
                act = 2;
                break;
            case 2:
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
                dialog.setText("Lidia: Those ghosts are very scary!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=4;
                }
                break;
            case 4:
                disableInput = false;
                dialog.setVisible(false);
                hero.setMana(0);
                grid.spawnEnemies(new Ghost(getAtlas(),120,5,0),new Bee(getAtlas(),70,5,2),new Ghost(getAtlas(),100,3,1),new Ghost(getAtlas(),120,4,1));
                act = 5;
                break;
            case 5:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 6;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 6:
                disableInput = false;
                dialog.setVisible(false);
                hero.setMana(0);
                grid.spawnEnemies(new Ghost(getAtlas(),100,5,0),new Ghost(getAtlas(),100,5,2),new Ghost(getAtlas(),100,5,1),new Bee(getAtlas(),60,3,1),new Bee(getAtlas(),80,4,2));
                act = 7;
                break;
            case 7:
                if(BattleGrid.checkState()==BattleGrid.battleWon){
                    act = 9;
                    stateTime = 0;
                }else  if(BattleGrid.checkState()==BattleGrid.battleLost&&stateTime>3){
                    dialog.setText("You were defeated! The hopes and dreams of Altera died with you...");
                    dialog.setVisible(true);
                    restartButton.setVisible(true);
                }
                break;
            case 9:
                dialog.setText("Level Up! Your Maximum HP has been raised to 250!");
                dialog.setVisible(true);
                if(stateTime>4){
                    act=10;
                }
                break;
            case 10:
                game.setScreen(new Chapter2_10(game));
                break;
        }
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        if(restartButton.isVisible()){
            this.getBatch().begin();
            restartButton.draw(this.getBatch(), 1);
            this.getBatch().end();
        }
    }
}
