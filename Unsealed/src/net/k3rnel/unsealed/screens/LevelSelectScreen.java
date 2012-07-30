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
import net.k3rnel.unsealed.story.chapters.Chapter1_1;
import net.k3rnel.unsealed.story.chapters.Chapter2_1;
import net.k3rnel.unsealed.story.chapters.Chapter2_6;

public class LevelSelectScreen extends AbstractScreen {

    private Table table;
    
    public LevelSelectScreen(Unsealed game) {
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
        
        Label titleLabel = new Label("Select Chapter", skin);
        table.padTop(40);
        table.add(titleLabel).expandX().align(Align.center).colspan(7);
        table.row();
        table.row();
        table.add();
        table.add();
        Label chapterLabel = new Label("Chapter 1\nNew Girl in Town",skin);
        chapterLabel.setAlignment(Align.center);
        table.add(chapterLabel).align(Align.center);
        chapterLabel = new Label("Chapter 2\nOld Friends",skin);
        table.add(chapterLabel).align(Align.center);
        chapterLabel = new Label("Chapter 3\n",skin);
        table.add(chapterLabel).align(Align.center);
        table.add();
        table.add();
        table.row();
        AtlasRegion charRegion = getAtlas().findRegion( "char-sprites/lidia_spritesheet" );
        TextureRegion[][] charTextures = charRegion.split(64,64);
        
        Image chapterImage = new Image(charTextures[2][0]);
        chapterImage.setScaling(Scaling.stretch);
        chapterImage.scale(1f);
        chapterImage.setOrigin(chapterImage.getWidth()/2,chapterImage.getHeight()/2);
        Button chapterButton = new Button(skin);
        chapterButton.add(chapterImage);
        chapterButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new Chapter1_1( game ) );
                
            }
        });
        table.add(new Button(new Label("<",skin),skin)).height(128).width(32);
        table.add().height(128).width(32);
        table.add(chapterButton).height(128).width(128);
        charRegion = getAtlas().findRegion( "char-sprites/lidia_spritesheet" );
        charTextures = charRegion.split(64,64);
        
        Image chap2Screen = new Image(charTextures[2][0]);
        chap2Screen.setScaling(Scaling.stretch);
        chap2Screen.scale(1f);
        chap2Screen.setOrigin(chap2Screen.getWidth()/2,chap2Screen.getHeight()/2);
        final Button chap2Button = new Button(skin);
        chap2Button.add(chap2Screen).left().bottom();
        chap2Button.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new Chapter2_1( game ) );
                
            }
        });
        chap2Button.size(128,128);
        table.add(chap2Button).height(128).width(128);
        
        Image chap3Screen = new Image(charTextures[2][0]);
        chap3Screen.setScaling(Scaling.stretch);
        chap3Screen.scale(1f);
        chap3Screen.setOrigin(chap3Screen.getWidth()/2,chap3Screen.getHeight()/2);
        final Button chap3Button = new Button(skin);
        chap3Button.add(chap3Screen).left().bottom();
        chap3Button.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new Chapter2_6( game) );
                
            }
        });
        chap3Button.size(128,128);
        table.add(chap3Button).height(128).width(128);
        table.add().height(128).width(32);
        table.add(new Button(new Label(">",skin),skin)).height(128).width(32);
        stage.addActor( table );
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
//        Table.drawDebug(stage);
    }
}
