package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.AbstractScreen;
import net.k3rnel.unsealed.screens.battle.BattleEnemy;
import net.k3rnel.unsealed.screens.battle.BattleHUD;

public class BattleScreen extends AbstractScreen {

    BattleHUD hud;
    
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
     
        hud = new BattleHUD(this.stage.getWidth(), stage.getHeight(),getAtlas());
        hud.spawnEnemies();
        
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage,hud));
    }
    @Override
    public void render(float delta) {
       
        
        // (1) process the game logic

        // update the actors
        stage.act( delta );
        hud.act(delta);
        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors

        hud.draw();
        stage.draw();
        
      
    }
    
    
    
    
    
    @Override
    public boolean keyDown(int keycode) {
        switch(keycode) {
            case Input.Keys.RIGHT:
                hud.buttonPress(3,true);
                return true;
            case Input.Keys.LEFT:
                hud.buttonPress(2,true);
                return true;
            case Input.Keys.UP:
                hud.buttonPress(0,true);
                return true;
            case Input.Keys.DOWN:
                hud.buttonPress(1,true);
                return true;
            case Input.Keys.U:
                hud.buttonPress(4,true);
                return true;
            case Input.Keys.O:
                hud.buttonPress(5,true);
                return true;
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.U:
                hud.buttonPress(4,false);
                return true;
            case Input.Keys.O:
                hud.buttonPress(5,false);
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
}
