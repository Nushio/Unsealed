package net.k3rnel.unsealed.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.PressedListener;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.AbstractScreen;
import net.k3rnel.unsealed.screens.battle.BattleEntity;
import net.k3rnel.unsealed.screens.battle.BattleHUD;
import net.k3rnel.unsealed.screens.battle.BattleHero;
import net.k3rnel.unsealed.screens.battle.BattleGrid;
import net.k3rnel.unsealed.screens.battle.magic.MagicEntity;

public class BattleScreen extends AbstractScreen {


    private Image background;
    private Image battleoverlay;

    private BattleGrid grid;
    private BattleHUD hud;

    public static BattleHero hero;

    private Label roundLabel;

    private Button restartButton;

    public static int round = 0;
    public static int bonus = 1;

    List<MagicEntity> magics = new ArrayList<MagicEntity>();

    public BattleScreen(Unsealed game) {
        super(game);
    }
    @Override
    protected boolean isGameScreen() {
        return false;
    }
    @Override
    public void show() {
        super.show();


        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage));
        atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages-info.atlas" ) );
        //        int battlemap = random.nextInt(6);
        AtlasRegion atlasRegion = atlas.findRegion( "battle/maps/battlemap-test");
        background = new Image(atlasRegion);
        background.setSize(stage.getWidth(), stage.getHeight());

        stage.addActor(background);

        atlasRegion = atlas.findRegion( "battle/ui/field-3x3" );
        battleoverlay = new Image(atlasRegion);
        battleoverlay.setX( stage.getWidth()/2 - battleoverlay.getWidth()/2 );
        battleoverlay.setY( stage.getHeight()/2 -battleoverlay.getHeight()- 25); 
        stage.addActor(battleoverlay);

        hud = new BattleHUD(this.stage.getWidth(), stage.getHeight());

        if(Gdx.app.getVersion()>0||Unsealed.DEBUG==true){
            hud.leftTrigger.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(4,true); 
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    buttonPress(4,false); 
                }
            });
            hud.rightTrigger.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(5,true); 
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    buttonPress(5,false); 
                }
            });
            hud.xButton.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(6,true); 
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    buttonPress(6,false); 
                }
            });
            hud.xButton.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(8,true); 
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                    buttonPress(8,false); 
                }
            });

            hud.dPadDown.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(1,true); 
                    return true;
                }
            });

            hud.dPadUp.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(0,true); 
                    return true;
                }
            });

            hud.dPadLeft.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(2,true); 
                    return true;
                }
            });

            hud.dPadRight.addListener(new PressedListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchDown(event, x, y, pointer, button);
                    buttonPress(3,true); 
                    return true;
                }
            });
        }
        grid = new BattleGrid(this.stage.getWidth(), stage.getHeight(),6,3);

        hero = new BattleHero(getAtlas(),100);
        hero.setGrid(1,1);
        grid.assignEntity(hero);     

        grid.spawnEnemies(bonus);

        roundLabel = new Label("Round "+round,getSkin());
        roundLabel.setX(350);
        roundLabel.setY(350);
        this.stage.addActor(roundLabel);
        OrthographicCamera cam = new OrthographicCamera(this.stage.getWidth(), this.stage.getHeight());   

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
                hero = new BattleHero(getAtlas(),100);
                hero.setGrid(1,1);
                grid.reset();
                grid.assignEntity(hero);     

                grid.spawnEnemies(bonus);
                bonus = 0;
                round = 1;
                restartButton.setVisible(false);
                restartButton.setDisabled(true);
            }
        });
        this.stage.addActor(restartButton);
        cam.position.set(this.stage.getWidth() / 2, this.stage.getHeight() / 2, 0);
        cam.zoom = 1f;
        this.stage.setCamera(cam);
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage,hud));
    } 

    @Override
    public void render(float delta) {
        super.render(delta);
        roundLabel.setText("Round "+round+"");


        if(BattleGrid.checkState() == BattleGrid.battleWon){
            bonus++;
            grid.spawnEnemies(bonus);
        }else if(BattleGrid.checkState() == BattleGrid.battleLost){
            roundLabel.setX(330);
            roundLabel.setText("You Lost at Round "+round+"!");
            restartButton.setVisible(true);

        }else if(BattleGrid.checkState() == BattleGrid.battleStarted){
            grid.act(delta);
            hud.act(delta);
        }

        hud.fillMana(hero);

        grid.draw();
        hud.draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.RIGHT:
                buttonPress(3,true);
                return true;
            case Input.Keys.D:
                buttonPress(3,true);
                return true;
            case Input.Keys.LEFT:
                buttonPress(2,true);
                return true;
            case Input.Keys.A:
                buttonPress(2,true);
                return true;
            case Input.Keys.UP:
                buttonPress(0,true);
                return true;
            case Input.Keys.W:
                buttonPress(0,true);
                return true;
            case Input.Keys.DOWN:
                buttonPress(1,true);
                return true;
            case Input.Keys.S:
                buttonPress(1,true);
                return true;
            case Input.Keys.U:
                buttonPress(4,true);
                return true;
            case Input.Keys.O:
                buttonPress(5,true);
                return true;
            case Input.Keys.J:
                buttonPress(6,true);
                return true;
            case Input.Keys.L:
                buttonPress(8,true);
                return true;
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.U:
                buttonPress(4,false);
                return true;
            case Input.Keys.O:
                buttonPress(5,false);
                return true;
            case Input.Keys.J:
                buttonPress(6,false);
                return true;
            case Input.Keys.L:
                buttonPress(8,false);
                return true;
            case Input.Keys.BACK:
                game.setScreen(new MenuScreen(game));
                return true;
            case Input.Keys.ESCAPE:
                game.setScreen(new MenuScreen(game));
                return true;
        }
        return false;
    }

    /**
     * 0 = up, 1 = down, 2 = left, 3 = right
     * 4 = shield, 5 = attack
     * 6 = Skill1. 7 = Skill2. 8 = Skill3.
     * @param direction
     */
    public void buttonPress(int button,boolean pressed){
        switch(button){
            //TODO: Hero moves at a fixed pixel rate (A.k.a. magic numbers). It should instead move based on screen width.
            case 0:{ // Up
                if(hero.getState()==BattleEntity.stateIdle){
                    if((hero.getGridYInt()-1>-1)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt(), hero.getGridYInt()-1);

                    }
                }
                break;
            }
            case 1:{ // Down
                if(hero.getState()==BattleEntity.stateIdle){
                    if((hero.getGridYInt()+1<3)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt(), hero.getGridYInt()+1);

                    }
                }
                break;
            }
            case 2:{ // Left
                if(hero.getState()==BattleEntity.stateIdle){
                    if((hero.getGridXInt()-1>-1)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt()-1, hero.getGridYInt());

                    }
                }
                break;
            }
            case 3:{ // Right
                if(hero.getState()==BattleEntity.stateIdle){
                    if((hero.getGridXInt()+1<3)){
                        BattleGrid.moveEntity(hero, hero.getGridXInt()+1, hero.getGridYInt());
                    }
                }
                break;
            }
            case 4:{ // Shield
                if(pressed){
                    if(hero.getState()!=BattleEntity.stateBlocking){
                        hero.setState(BattleEntity.stateBlocking);
                        hero.showShield(true);
                        hero.setMana(hero.getMana()-1);
                    }
                }else{
                    hero.showShield(false);
                    hero.setState(BattleEntity.stateIdle);
                }
                break;
            }
            case 5:{ // Attack
                if(pressed){

                }else{
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>1){
                            hero.magicType=0;
                            hero.setMana(hero.getMana()-1);
                            hero.setState(BattleEntity.stateAttacking);
                        }
                    }
                }
                break;
            }
            case 6:{ // Skill1
                if(pressed){
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>5){
                            hero.setMana(hero.getMana()-5);
                            hero.setState(BattleEntity.stateAltAttacking);
                        }
                    }
                }else{

                }
                break;
            }
            case 8:{ // Skill3
                if(pressed){
                    if(hero.getState()==BattleEntity.stateIdle){
                        if(hero.getMana()>10){
                            hero.setMana(hero.getMana()-10);
                            hero.magicType=1;
                            hero.setState(BattleEntity.stateAttacking);
                        }
                    }
                }else{

                }
                break;
            }
        }
    }





}
