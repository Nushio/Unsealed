package net.k3rnel.unsealed.story.chapters;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.battle.BattleGrid;
import net.k3rnel.unsealed.battle.enemies.Xios;
import net.k3rnel.unsealed.battle.skills.EarthSpikes;
import net.k3rnel.unsealed.battle.skills.FireLion;
import net.k3rnel.unsealed.battle.skills.FirePunch;
import net.k3rnel.unsealed.battle.skills.IceTentacle;
import net.k3rnel.unsealed.battle.skills.ThunderClaw;
import net.k3rnel.unsealed.battle.skills.TornadoVacuum;
import net.k3rnel.unsealed.screens.BattleScreen;
import net.k3rnel.unsealed.screens.LevelSelectScreen;

public class Chapter3_4 extends BattleScreen {

    protected ImageButton restartButton;

    public Chapter3_4(Unsealed game) {
        super(game,true, "Boss area");

    }

    @Override
    public void show() {
        super.show();
        hero.setHp(250);
        hero.setSkill1(new EarthSpikes(getAtlas()));
        hud.xButton.addActor(hero.getSkill1());
        
        hero.setSkill2(new FirePunch(getAtlas()));
        hud.bButton.addActor(hero.getSkill2());
        
        hero.setSkill3(new ThunderClaw(getAtlas()));
        hud.aButton.addActor(hero.getSkill3());
        
        hero.setSkill4(new FireLion(getAtlas()));
        hero.setSkill5(new IceTentacle(getAtlas()));
        hero.setSkill6(new TornadoVacuum(getAtlas()));
        
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
               hero.setHp(250);
               hero.setMana(0);
               hero.setGrid(1,1);
               hero.setSkill1(new EarthSpikes(getAtlas()));
               hud.xButton.addActor(hero.getSkill1());
               
               hero.setSkill2(new FirePunch(getAtlas()));
               hud.bButton.addActor(hero.getSkill2());
               
               hero.setSkill3(new ThunderClaw(getAtlas()));
               hud.aButton.addActor(hero.getSkill3());
               
               hero.setSkill4(new FireLion(getAtlas()));
               hero.setSkill5(new IceTentacle(getAtlas()));
               hero.setSkill6(new TornadoVacuum(getAtlas()));
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
        if(restartButton.isVisible()){
            this.getBatch().begin();
            restartButton.draw(this.getBatch(), 1);
            this.getBatch().end();
        }
    }
    @Override
    public void checkScene(float delta){
        this.stateTime+=delta;
        camera.position.set(950, 1700, 0);
        camera.zoom = 1f;
        camera.update();
        switch(act){
            case -1:
                dialog.setVisible(true);
                dialog.setText("I need to concentrate and use my Skills appropriately");
                if(stateTime>4){
                    grid.reset();
                    hero.setHp(180);
                    hero.setMana(0);
                    grid.assignEntity(hero);
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
                grid.spawnEnemies(new Xios(getAtlas(),1200,4,1));
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
                game.setScreen(new LevelSelectScreen(game));
                break;
        }
    }
}
