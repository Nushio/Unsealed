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

import java.util.Locale;

import net.k3rnel.unsealed.Unsealed;
import net.k3rnel.unsealed.services.MusicManager.UnsealedMusic;
import net.k3rnel.unsealed.services.SoundManager.UnsealedSound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen extends AbstractScreen {

    private Label volumeValue;

    public OptionsScreen( Unsealed game ) {
        super( game );
    }

    @Override
    public void show() {
        super.show();

       
        // create the table actor and add it to the stage
        Table table = super.getTable();

        table.defaults().spaceBottom( 30 );
        table.columnDefaults( 0 ).padRight( 20 );
        table.add( "Options" ).colspan( 3 );

        
        table.row();
        table.add( "Sound Effects" );
        final CheckBox soundEffectsCheckbox = new CheckBox("", getSkin() );
        table.add( soundEffectsCheckbox ).colspan( 2 ).left();
        soundEffectsCheckbox.setChecked( game.getPreferencesManager().isSoundEnabled() );
        soundEffectsCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor ) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                game.getPreferencesManager().setSoundEnabled( enabled );
                game.getSoundManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );
            }
        } );
        
        table.row();
        table.add( "Music" );
        final CheckBox musicCheckbox = new CheckBox("", getSkin() );
        table.add( musicCheckbox ).colspan( 2 ).left();
        musicCheckbox.setChecked( game.getPreferencesManager().isMusicEnabled() );
        musicCheckbox.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor ) {
                boolean enabled = musicCheckbox.isChecked();
                game.getPreferencesManager().setMusicEnabled( enabled );
                game.getMusicManager().setEnabled( enabled );
                game.getSoundManager().play( UnsealedSound.CLICK );

                // if the music is now enabled, start playing the menu music
                if( enabled ) game.getMusicManager().play( UnsealedMusic.MENU );
            }
        } );
       


        // add the volume row
        table.row();
        table.add( "Volume" );
        // range is [0.0,1.0]; step is 0.1f
        Slider volumeSlider = new Slider( 0f, 1f, 0.1f, false, getSkin() );
        table.add( volumeSlider );
        volumeSlider.setValue( game.getPreferencesManager().getVolume() );
        volumeSlider.addListener( new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float value = ( (Slider) actor ).getValue();
                game.getPreferencesManager().setVolume( value );
                game.getMusicManager().setVolume( value );
                game.getSoundManager().setVolume( value );
                updateVolumeLabel();

            }
        } );
        // create the volume label
        volumeValue = new Label( "", getSkin() );

        table.add( volumeValue ).width( 40 );
        updateVolumeLabel();

       


        TextButton backButton = new TextButton( "Back to Main Menu", getSkin() );
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y ) {
                game.getSoundManager().play( UnsealedSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        } );
        table.row();
        table.add( backButton ).size( 250, 60 ).colspan( 3 );

        Gdx.input.setInputProcessor(new InputMultiplexer(this,stage));
    }

    /**
     * Updates the volume label next to the slider.
     */
    private void updateVolumeLabel() {
        float volume = ( game.getPreferencesManager().getVolume() * 100 );
        volumeValue.setText( String.format( Locale.US, "%1.0f%%", volume ) );
        Gdx.app.log( Unsealed.LOG, "Volume Value: " + volume );
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
