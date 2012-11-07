/**
 * Unsealed: Whispers of Wisdom. 
 * 
 * Copyright (C) 2012 - Juan 'Nushio' Rodriguez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 of 
 * the License as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package net.k3rnel.unsealed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
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
import net.k3rnel.unsealed.story.chapter1.Chapter1_1;
import net.k3rnel.unsealed.story.chapter2.Chapter2_1;
import net.k3rnel.unsealed.story.chapter3.Chapter3_1;

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
        table = super.getTable();
        if(Unsealed.DEBUG)
            table.debug();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.pad(10).defaults().spaceBottom(10);
        
        Label titleLabel = new Label("Select your character", skin);
        table.padTop(40);
        table.add(titleLabel).expandX().align(Align.center).colspan(4);
        table.row();
        table.row();
        table.add();
        Label chapterLabel = new Label("Lidia Terius",skin);
        chapterLabel.setAlignment(Align.center);
        table.add(chapterLabel).align(Align.center);
        chapterLabel = new Label("Lemond Phaunt",skin);
        chapterLabel.setAlignment(Align.center);
        table.add(chapterLabel).align(Align.center);
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
        table.add().height(128).width(32);
        table.add(chapterButton).height(128).width(128);
        charRegion = getAtlas().findRegion( "char-sprites/lidia_spritesheet" );
        charTextures = charRegion.split(64,64);
        
        Image chap2Screen = new Image(charTextures[2][0]);
        chap2Screen.setScaling(Scaling.stretch);
        chap2Screen.scale(1f);
        chap2Screen.setOrigin(chap2Screen.getWidth()/2,chap2Screen.getHeight()/2);
        final Button chap2Button = new Button(skin);
        chap2Button.add().left().bottom();
        chap2Button.addListener(new ClickListener() {
            
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen( new Chapter2_1( game ) );
                
            }
        });
        chap2Button.size(128,128);
        table.add(chap2Button).height(128).width(128);
        table.add();
       
        
        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage));
    }
    
    /**
     * On key press
     */
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
