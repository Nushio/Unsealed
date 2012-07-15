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
    Image guy;
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
     
        hud = new BattleHUD(this.stage.getWidth(), stage.getHeight());
        AtlasRegion charRegion = getAtlas().findRegion( "battle/lidia" );
        guy = new Image(charRegion);
        guy.setScaling(Scaling.stretch);
        guy.setScale(0.5f);
        guy.setPosition(234,196);
        stage.addActor(guy);
        charRegion = getAtlas().findRegion( "battle/lifebar" );
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
        guy.setScale(0.9f);
        
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
                if(guy.getX()<322)
                guy.setX(guy.getX()+88);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.LEFT:
                if(guy.getX()>146)
                    guy.setX(guy.getX()-88);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.UP:
                if(guy.getY()<196)
                    guy.setY(guy.getY()+40);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.DOWN:
                if(guy.getY()>116)
                    guy.setY(guy.getY()-40);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.D:
                if(gal.getX()<526)
                    gal.setX(gal.getX()+76);
                Gdx.app.log(Unsealed.LOG, "Position: "+gal.getX()+"/"+gal.getY());
                return true;
            case Input.Keys.A:
                if(gal.getX()>374)
                    gal.setX(gal.getX()-76);
                Gdx.app.log(Unsealed.LOG, "Position: "+gal.getX()+"/"+gal.getY());
                return true;
            case Input.Keys.W:
                if(gal.getY()<115)
                gal.setY(gal.getY()+31);
                Gdx.app.log(Unsealed.LOG, "Position: "+gal.getX()+"/"+gal.getY());
                return true;
            case Input.Keys.S:
                if(gal.getY()>53)
                gal.setY(gal.getY()-31);
                Gdx.app.log(Unsealed.LOG, "Position: "+gal.getX()+"/"+gal.getY());
                return true;
        }
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        switch(keycode) {
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
