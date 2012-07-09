package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.screens.AbstractScreen;

public class BattleScreen extends AbstractScreen {

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
        AtlasRegion atlasRegion = getAtlas().findRegion( "battle/field-4x4" );
        Image battlefield = new Image( atlasRegion);
        battlefield.setScaling(Scaling.stretch);
        battlefield.setScale(2f);
        battlefield.setOriginX((battlefield.getWidth()*battlefield.getScaleX())/2 - stage.getWidth()/2 );
        battlefield.setOriginY(-10);
        stage.addActor(battlefield);
        atlasRegion = getAtlas().findRegion( "char-sprites/male_spritesheet" );
        Image guy = new Image( atlasRegion);
        guy.setScaling(Scaling.stretch);
        guy.setScale(2f);
        stage.addActor(guy);
        atlasRegion = getAtlas().findRegion( "char-sprites/female_spritesheet" );
        Image gal = new Image( atlasRegion);
        gal.setScaling(Scaling.fill);
        gal.setScale(2f);
        gal.setPosition(60,60);
        stage.addActor(gal);
    }
}
