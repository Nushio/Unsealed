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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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

    public BattleHero hero;


    private int bonus = 1;

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
        
        grid = new BattleGrid(this.stage.getWidth(), stage.getHeight(),6,3);

        hero = new BattleHero(getAtlas(),100);
        hero.setGrid(1,1);
        grid.assignEntity(hero);     

        grid.spawnEnemies(bonus);
        
        OrthographicCamera cam = new OrthographicCamera(this.stage.getWidth(), this.stage.getHeight());   
        
        cam.position.set(this.stage.getWidth() / 2, this.stage.getHeight() / 2, 0);
        cam.zoom = 0.76f;
        this.stage.setCamera(cam);
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage,hud));
    } 

    @Override
    public void render(float delta) {
        super.render(delta);
        grid.act(delta);
        hud.act(delta);
        
        hud.fillMana(hero);
        
        grid.draw();
        hud.draw();

       if(grid.checkState() == BattleGrid.battleWon){
           bonus++;
           grid.spawnEnemies(bonus);
       }
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
     * @param direction
     */
    public void buttonPress(int button,boolean pressed){
        switch(button){
            //TODO: Hero moves at a fixed pixel rate (A.k.a. magic numbers). It should instead move based on screen width.
            case 0:{ // Up
                if((hero.getGridY()-1>-1)){
                    grid.moveEntity(hero, hero.getGridX(), hero.getGridY()-1);
                    
                }
                break;
            }
            case 1:{ // Down
                if((hero.getGridY()+1<3)){
                    grid.moveEntity(hero, hero.getGridX(), hero.getGridY()+1);
                    
                }
                break;
            }
            case 2:{ // Left
                if((hero.getGridX()-1>-1)){
                    grid.moveEntity(hero, hero.getGridX()-1, hero.getGridY());
                    
                }
                break;
            }
            case 3:{ // Right
                if((hero.getGridX()+1<3)){
                    grid.moveEntity(hero, hero.getGridX()+1, hero.getGridY());
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
                    hero.isCharging = true;
                }else{
                    if(!hero.getBlast().isVisible()){
                        hero.setMana(hero.getMana()-1);
                        hero.setState(BattleEntity.stateAttacking);
                    }
                }
                break;
            }
        }
    }

   

    

}
