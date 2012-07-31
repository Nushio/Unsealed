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

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen {
    
    private Table table;

    public MenuScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();

        // retrieve the custom skin for our 2D widgets
        Skin skin = super.getSkin();
        game.getMusicManager().play( UnsealedMusic.MENU );
        // create the table actor and add it to the stage
        table = super.getTable();
        table.setWidth(stage.getWidth());
        table.setHeight(stage.getHeight());
        table.pad(10).defaults().spaceBottom(10).space(5);
        table.row().fill().expandX();
        AtlasRegion splashRegion = getAtlas().findRegion( "splash-screen/menulogo" );
        Image logo = new Image(splashRegion);
        table.add(logo).fill(false);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
        TextButton storyModeButton = new TextButton( "Story Mode", skin );
        storyModeButton.setVisible(true);
        storyModeButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                game.setScreen( new ChapterSelectScreen( game ) );
            }
        } );
        table.add(storyModeButton).size( 300, 60 ).uniform().spaceBottom(10);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
        TextButton battleArenaButton = new TextButton( "Battle Arena", skin );
        battleArenaButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                if(Unsealed.DEBUG)
                    game.setScreen( new BattleScreen( game,false ,"TownOne") );
                else
                    game.setScreen( new QuickTutorialScreen( game ) );
            }
        } );
        table.add(battleArenaButton).size( 300, 60 ).uniform().spaceBottom(10);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
        TextButton optionsButton = new TextButton( "Options", skin );
        optionsButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                    game.setScreen( new OptionsScreen( game) );
            }
        } );
        table.add(optionsButton).size( 300, 60 ).uniform().spaceBottom(10);
        table.row();
        table.pad(10).defaults().spaceBottom(10);
    }
}
