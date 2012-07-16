package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.objects.BattleHUD;
import net.k3rnel.unsealed.screens.AbstractScreen;

public class BattleScreen extends AbstractScreen {

    BattleHUD hud;
    Image gal;
    Image galbar;
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
       
        AtlasRegion charRegion = getAtlas().findRegion( "battle/lifebar" );
        TextureRegion[][] charTextures = charRegion.split(28,8);
        charRegion = getAtlas().findRegion( "char-sprites/female_spritesheet" );
        charTextures = charRegion.split(64,64);
        gal = new Image(charTextures[1][8]);
        gal.setScaling(Scaling.fill);
        gal.setScale(2f);
        gal.setPosition(450,84);
        charRegion = getAtlas().findRegion( "battle/enemy-lifebar" );
        charTextures = charRegion.split(106,19);
        galbar = new Image(charTextures[0][0]);
//        galbar.setScale(2f);
        galbar.setPosition(450,104);
//        stage.addActor(galbar);
//        stage.addActor(gal);
        
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage,hud));
    }
    @Override
    public void render(float delta) {
       
        
        // (1) process the game logic

        // update the actors
        stage.act( delta );

        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors

        hud.draw();
        stage.draw();
        
        galbar.setX(gal.getX()+17);
        galbar.setY(gal.getY()+100);
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
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
            case Input.Keys.U:
                hud.buttonPress(4,false);
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
