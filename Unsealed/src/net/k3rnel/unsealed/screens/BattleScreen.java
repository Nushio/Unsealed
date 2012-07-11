package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.AbstractScreen;

public class BattleScreen extends AbstractScreen {

    Image guy;
    Image gal;
    Image battlefield;
    Image guybar;
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
        // retrieve the battle area from the atlas
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/field-3x3" );
        battlefield = new Image( atlasRegion);
        battlefield.setScaling(Scaling.stretch);
        battlefield.setScale(2f);
        battlefield.setOriginX((battlefield.getWidth()*battlefield.getScaleX())/2 - stage.getWidth()/2 );
        battlefield.setOriginY((battlefield.getHeight()*battlefield.getScaleY())+40 - stage.getHeight()/2 );
        stage.addActor(battlefield);
        AtlasRegion charRegion = getAtlas().findRegion( "char-sprites/male_spritesheet" );
        TextureRegion[][] charTextures = charRegion.split(64,64);
        guy = new Image(charTextures[3][0]);
        guy.setScaling(Scaling.stretch);
        guy.setPosition(222,84);
        guy.setScale(2f);
        stage.addActor(guy);
        charRegion = getAtlas().findRegion( "battle/lifebar" );
        charTextures = charRegion.split(28,8);
        guybar = new Image(charTextures[0][0]);
        guybar.setScale(2f);
        guybar.setPosition(222,104);
        stage.addActor(guybar);
        charRegion = getAtlas().findRegion( "char-sprites/female_spritesheet" );
        charTextures = charRegion.split(64,64);
        gal = new Image(charTextures[1][0]);
        gal.setScaling(Scaling.fill);
        gal.setScale(2f);
        gal.setPosition(450,84);
        charRegion = getAtlas().findRegion( "battle/lifebar" );
        charTextures = charRegion.split(28,8);
        galbar = new Image(charTextures[0][0]);
        galbar.setScale(2f);
        galbar.setPosition(450,104);
        stage.addActor(galbar);
        stage.addActor(gal);
        
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage));
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        guybar.setX(guy.getX()+33);
        guybar.setY(guy.getY()+100);
        galbar.setX(gal.getX()+37);
        galbar.setY(gal.getY()+100);
    }
    @Override
    public boolean keyDown(int keycode) {
        
        switch(keycode) {
            case Input.Keys.RIGHT:
                if(guy.getX()<298)
                guy.setX(guy.getX()+76);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.LEFT:
                if(guy.getX()>146)
                    guy.setX(guy.getX()-76);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.UP:
                if(guy.getY()<115)
                    guy.setY(guy.getY()+31);
                Gdx.app.log(Unsealed.LOG, "Position: "+guy.getX()+"/"+guy.getY());
                return true;
            case Input.Keys.DOWN:
                if(guy.getY()>53)
                    guy.setY(guy.getY()-31);
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
            case Input.Keys.Q:
                battlefield.setY((battlefield.getHeight()*battlefield.getScaleY())+40 - stage.getHeight()/2 );
                return true;

        }
        return false;
    }
}
