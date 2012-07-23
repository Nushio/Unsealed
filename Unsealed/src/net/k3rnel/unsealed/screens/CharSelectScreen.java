package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;

import net.k3rnel.unsealed.Unsealed;

public class CharSelectScreen extends AbstractScreen {

    private Table table;
    
    public CharSelectScreen(Unsealed game) {
        super(game);
    }
    
    @Override
    protected boolean isGameScreen() {
        return false;
    }
    
    @Override
    public void show() {
        super.show();
        
        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();

        // create the table actor and add it to the stage
        table = new Table( skin );
        if(Unsealed.DEBUG)
            table.debug();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.pad(10).defaults().spaceBottom(10);
        
        final Label titleLabel = new Label("Select Game Mode", skin);
        table.padTop(40);
        table.add(titleLabel).expandX().align(Align.center).colspan(2);
        table.row();
        table.row();
        final Label guyLabel = new Label("Survival",skin);
        final Label galLabel = new Label("Battle",skin);
        table.add(guyLabel).align(Align.center);
        table.add(galLabel).align(Align.center);
        table.row();
        AtlasRegion charRegion = getAtlas().findRegion( "char-sprites/female_spritesheet" );
        TextureRegion[][] charTextures = charRegion.split(64,64);
        
        Image overworld = new Image(charTextures[2][0]);
        overworld.setScaling(Scaling.stretch);
        overworld.scale(1f);
        overworld.setOrigin(overworld.getWidth()/2,overworld.getHeight()/2);
        final Button guyButton = new Button(skin);
        guyButton.add(overworld);
        guyButton.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new OverworldScreen( game ) );
                
            }
        });
        table.add(guyButton).height(128).width(128);
        charRegion = getAtlas().findRegion( "char-sprites/female_spritesheet" );
        charTextures = charRegion.split(64,64);
        
        Image battle = new Image(charTextures[2][0]);
        battle.setScaling(Scaling.stretch);
        battle.scale(1f);
        battle.setOrigin(battle.getWidth()/2,battle.getHeight()/2);
        final Button galButton = new Button(skin);
        galButton.add(battle).left().bottom();
        galButton.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new BattleScreen( game ) );
                
            }
        });
        galButton.size(128,128);
        table.add(galButton).height(128).width(128);
        stage.addActor( table );
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
//        Table.drawDebug(stage);
    }
}
